package com.rsddm.marketplace.features.products.detail

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.core.toDoubleFromCurrency
import com.rsddm.marketplace.core.toStringCurrency
import com.rsddm.marketplace.features.products.ProductsRoutes
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import data.session.ShoppingCartSession
import domain.entities.Product
import domain.entities.ProductDetail
import domain.entities.ShoppingCartProduct
import domain.useCases.GetProductDetailUseCase
import domain.useCases.GetProductDetailUseCaseFactory
import domain.useCases.UpdateShoppingCartProductUseCase
import domain.useCases.UpdateShoppingCartProductUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ProductDetailViewModel(product: Product, navigator: Navigator) :
    BaseViewModel(navigator) {

    private val getProductDetailUseCase: GetProductDetailUseCase by GetProductDetailUseCaseFactory()
    private val updateShoppingCartProductUseCase: UpdateShoppingCartProductUseCase by UpdateShoppingCartProductUseCaseFactory()

    private val _uiState: MutableStateFlow<ProductDetailUIState> =
        MutableStateFlow(ProductDetailUIState.Loading(ProductDetail(product = product)))
    val uiState: StateFlow<ProductDetailUIState> = _uiState

    init {
        viewModelScope.launch {
            getProductDetailUseCase.execute(product) {
                when (it) {
                    is Resource.Error -> throw Exception()
                    is Resource.Success -> _uiState.value = ProductDetailUIState.Detailed(
                        it.data.copy(
                            installment = "em até 12x de ${(product.price.toDoubleFromCurrency() / 12).toStringCurrency()}"
                        )
                    )
                }
            }
        }
    }

    fun onProductClick(product: Product) {
        navigator.navigate(ProductsRoutes.Detail, product)
    }

    fun onBuyClick(productDetail: ProductDetail) {
        viewModelScope.launch {
            updateShoppingCartProductUseCase.execute(ShoppingCartProduct(
                productDetail.product.name,
                productDetail.product.price,
            )) { }
        }

        navigator.navigate(AppRoutes.Shopping)
    }

    fun onAddToCartClick(productDetail: ProductDetail) {
        viewModelScope.launch {
            updateShoppingCartProductUseCase.execute(ShoppingCartProduct(
                productDetail.product.name,
                productDetail.product.price,
            )) { }
        }
    }

    override fun setupTopBar(title: String) {
        navigator.setState(NavigatorState.Navigation(title = title))
    }

    companion object {
        fun provideFactory(
            product: Product,
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ProductDetailViewModel(
                    product = product,
                    navigator = navigator
                )
            }
        }
    }
}