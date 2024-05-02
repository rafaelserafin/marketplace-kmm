package com.rsddm.marketplace.features.products.detail

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.features.products.ProductsRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import domain.entities.Product
import domain.entities.ProductDetail
import domain.useCases.GetProductDetailUseCase
import domain.useCases.GetProductDetailUseCaseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ProductDetailViewModel(product: Product, navigator: Navigator) :
    BaseViewModel(navigator) {

    private val getProductDetailUseCase: GetProductDetailUseCase by GetProductDetailUseCaseCaseFactory()

    private val _uiState: MutableStateFlow<ProductDetailUIState> =
        MutableStateFlow(ProductDetailUIState.Loading(ProductDetail(product = product)))
    val uiState: StateFlow<ProductDetailUIState> = _uiState

    init {
        navigator.setState(NavigatorState.Navigation(title = "Detalhes"))

        viewModelScope.launch {
            getProductDetailUseCase.execute(product) {
                when (it) {
                    is Resource.Error -> throw Exception()
                    is Resource.Success -> _uiState.value = ProductDetailUIState.Detailed(it.data)
                }
            }
        }
    }

    fun onProductClick(product: Product) {
        navigator.navigate(ProductsRoutes.Detail, product)
    }

    fun onBuyClick(productDetail: ProductDetail) {

    }

    fun onAddToCartClick(productDetail: ProductDetail) {
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