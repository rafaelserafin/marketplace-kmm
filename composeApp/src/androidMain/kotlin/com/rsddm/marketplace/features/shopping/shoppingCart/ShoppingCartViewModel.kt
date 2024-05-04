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
import data.api.errors.StockLackException
import data.api.errors.TransactionException
import data.api.errors.UnauthorizedException
import data.session.ShoppingCartSession
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import domain.useCases.FinalizePurchaseUseCase
import domain.useCases.FinalizePurchaseUseCaseFactory
import domain.useCases.UpdateShoppingCartProductUseCase
import domain.useCases.UpdateShoppingCartProductUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartViewModel(navigator: Navigator) : BaseViewModel(navigator) {

    private val updateShoppingCartProductUseCase: UpdateShoppingCartProductUseCase by UpdateShoppingCartProductUseCaseFactory()
    private val finalizePurchaseUseCase: FinalizePurchaseUseCase by FinalizePurchaseUseCaseFactory()

    private val _state = MutableStateFlow<ShoppingCartUIState>(ShoppingCartUIState.Empty)
    val state: StateFlow<ShoppingCartUIState> = _state.asStateFlow()

    private var products: List<ShoppingCartProduct> = listOf()

    init {
        viewModelScope.launch {
            ShoppingCartSession.state.collect {
                if (it.isEmpty()) {
                    _state.value = ShoppingCartUIState.Empty
                } else {
                    _state.value = ShoppingCartUIState.CheckIn(it, calculateTotalAmount(it))
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

    fun updateProductQuantity(product: ShoppingCartProduct, quantity: Int) {
        viewModelScope.launch {
            updateShoppingCartProductUseCase.execute(product) {

            }
        }
    }

    fun onBuyClick() {
        viewModelScope.launch {
            _state.value = ShoppingCartUIState.FinalizingPurchase

            finalizePurchaseUseCase.execute(
                ShoppingOrder(
                    products = products,
                    totalAmount = calculateTotalAmount(products),
                    "creditCard",
                    deliveryForecast = "",
                    System.currentTimeMillis()
                )
            ) {
                when (it) {
                    is Resource.Error -> _state.value =
                        when (it.exception) {
                            is UnauthorizedException -> ShoppingCartUIState.OrderError(
                                it.exception?.message.orEmpty(),
                                "ir para o login"
                            ) {
                                navigator.navigateAndPop(AppRoutes.Profile, AppRoutes.Shopping)
                            }

                            is StockLackException,
                            is TransactionException -> ShoppingCartUIState.OrderError(
                                it.exception?.message.orEmpty(),
                                "revisar pedido"
                            ) {
                                _state.value = ShoppingCartUIState.CheckIn(
                                    products,
                                    calculateTotalAmount(products)
                                )
                            }

                            else -> ShoppingCartUIState.OrderError(
                                it.exception?.message.orEmpty(),
                                "tentar novamente"
                            ) {
                                _state.value = ShoppingCartUIState.CheckIn(
                                    products,
                                    calculateTotalAmount(products)
                                )
                            }
                        }

                    is Resource.Success -> ShoppingCartUIState.OrderSuccess(it.data)
                }
            }


        }

    }

    override fun setupTopBar(title: String) {
        when (_state.value) {
            is ShoppingCartUIState.CheckIn -> navigator.setState(
                navigatorState = NavigatorState.CleanNavigation(
                    title
                )
            )

            ShoppingCartUIState.Empty -> navigator.setState(
                navigatorState = NavigatorState.CleanNavigation(
                    title
                )
            )

            ShoppingCartUIState.FinalizingPurchase -> navigator.setState(
                navigatorState = NavigatorState.CleanNavigation(
                    title
                )
            )

            is ShoppingCartUIState.OrderError -> navigator.setState(navigatorState = NavigatorState.None)
            is ShoppingCartUIState.OrderSuccess -> navigator.setState(navigatorState = NavigatorState.None)
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