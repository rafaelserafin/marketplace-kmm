package com.rsddm.marketplace.features.shopping.shoppingCart

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.core.toDoubleFromCurrency
import com.rsddm.marketplace.core.toStringCurrency
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import common.errors.UnauthorizedException
import data.api.errors.StockLackException
import data.api.errors.TransactionException
import data.session.ShoppingCartSession
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import domain.useCases.FinalizePurchaseUseCase
import domain.useCases.FinalizePurchaseUseCaseFactory
import domain.useCases.UpdateShoppingCartProductUseCase
import domain.useCases.UpdateShoppingCartProductUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ShoppingCartViewModel(navigator: Navigator) :
    BaseViewModel<ShoppingCart.UIState, ShoppingCart.ActionBundle>(navigator),
    ShoppingCart.ActionBundle {

    private val updateShoppingCartProductUseCase: UpdateShoppingCartProductUseCase by UpdateShoppingCartProductUseCaseFactory()
    private val finalizePurchaseUseCase: FinalizePurchaseUseCase by FinalizePurchaseUseCaseFactory()

    override val _uiState = MutableStateFlow<ShoppingCart.UIState>(ShoppingCart.UIState.Empty)
    override val actionBundle: ShoppingCart.ActionBundle = this

    private var products: List<ShoppingCartProduct> = listOf()

    init {
        viewModelScope.launch {
            ShoppingCartSession.state.collect {
                if (it.isEmpty()) {
                    setUIState(ShoppingCart.UIState.Empty)
                } else {
                    setUIState(ShoppingCart.UIState.CheckIn(it, calculateTotalAmount(it)))
                }
            }
        }

        viewModelScope.launch {
            ShoppingCartSession.state.collect {
                products = it
            }
        }
    }

    private fun calculateTotalAmount(products: List<ShoppingCartProduct>): String {
        val totalAmount = products.sumOf {
            it.quantity * it.price.toDoubleFromCurrency()
        }

        return totalAmount.toStringCurrency()
    }

    override fun updateProductQuantity(product: ShoppingCartProduct, quantity: Int) {
        viewModelScope.launch {
            updateShoppingCartProductUseCase.execute(
                product.copy(
                    quantity = quantity
                )
            ).collect()
        }
    }

    override fun goToMyOrders() {
        navigator.navigate(AppRoutes.Profile)
    }

    override fun onBuyClick() {
        viewModelScope.launch {
            setUIState(ShoppingCart.UIState.FinalizingPurchase)

            finalizePurchaseUseCase.execute(
                ShoppingOrder(
                    products = products,
                    totalAmount = calculateTotalAmount(products),
                    "creditCard",
                    deliveryForecast = "",
                    System.currentTimeMillis()
                )
            ) {
                val uiState = when (it) {
                    is Resource.Error ->
                        when (it.exception) {
                            is UnauthorizedException -> ShoppingCart.UIState.OrderError(
                                it.exception?.message.orEmpty(),
                                "ir para o login"
                            ) {
                                navigator.navigateAndPop(AppRoutes.Profile, AppRoutes.Shopping)
                            }

                            is StockLackException,
                            is TransactionException -> ShoppingCart.UIState.OrderError(
                                it.exception?.message.orEmpty(),
                                "revisar pedido"
                            ) {
                                setUIState(
                                    ShoppingCart.UIState.CheckIn(
                                        products,
                                        calculateTotalAmount(products)
                                    )
                                )
                            }

                            else -> ShoppingCart.UIState.OrderError(
                                it.exception?.message.orEmpty(),
                                "tentar novamente"
                            ) {
                                setUIState(
                                    ShoppingCart.UIState.CheckIn(
                                        products,
                                        calculateTotalAmount(products)
                                    )
                                )
                            }
                        }

                    is Resource.Success -> ShoppingCart.UIState.OrderSuccess(it.data)
                }

                setUIState(uiState)
            }
        }
    }

    override fun setupTopBar(title: String) {
        when (uiState.value) {
            is ShoppingCart.UIState.CheckIn -> navigator.setState(
                navigatorState = NavigatorState.CleanNavigation(
                    title
                )
            )

            ShoppingCart.UIState.Empty -> navigator.setState(
                navigatorState = NavigatorState.CleanNavigation(
                    title
                )
            )

            ShoppingCart.UIState.FinalizingPurchase -> navigator.setState(
                navigatorState = NavigatorState.CleanNavigation(
                    title
                )
            )

            is ShoppingCart.UIState.OrderError -> navigator.setState(navigatorState = NavigatorState.None)
            is ShoppingCart.UIState.OrderSuccess -> navigator.setState(navigatorState = NavigatorState.None)
        }
    }

    companion object {
        fun provideFactory(
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ShoppingCartViewModel(
                    navigator = navigator
                )
            }
        }
    }

}