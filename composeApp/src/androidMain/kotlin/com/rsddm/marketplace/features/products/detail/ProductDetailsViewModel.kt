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
import domain.entities.Product
import domain.entities.ProductDetail
import domain.entities.ShoppingCartProduct
import domain.useCases.GetProductDetailUseCase
import domain.useCases.GetProductDetailUseCaseProvider
import domain.useCases.UpdateShoppingCartProductUseCase
import domain.useCases.UpdateShoppingCartProductUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel(product: Product, navigator: Navigator) :
    BaseViewModel<ProductDetails.UIState,
            ProductDetails.ActionBundle>(navigator),
    ProductDetails.ActionBundle {

    private val getProductDetailUseCase: GetProductDetailUseCase by GetProductDetailUseCaseProvider()
    private val updateShoppingCartProductUseCase: UpdateShoppingCartProductUseCase by UpdateShoppingCartProductUseCaseProvider()

    override val _uiState: MutableStateFlow<ProductDetails.UIState> = MutableStateFlow(ProductDetails.UIState.Loading(ProductDetail(product = product)))
    override val actionBundle: ProductDetails.ActionBundle = this

    init {
        viewModelScope.launch {
            getProductDetailUseCase.execute(product) {
                when (it) {
                    is Resource.Error -> throw Exception()
                    is Resource.Success -> _uiState.value = ProductDetails.UIState.Detailed(
                        it.data.copy(
                            installment = "em até 12x de ${(product.price.toDoubleFromCurrency() / 12).toStringCurrency()}"
                        )
                    )
                }
            }
        }
    }

    override fun onProductClick(product: Product) {
        navigator.navigate(ProductsRoutes.Detail, product)
    }

    override fun onBuyClick(productDetail: ProductDetail) {
        viewModelScope.launch {
            updateShoppingCartProductUseCase.execute(
                ShoppingCartProduct(
                    productDetail.product.name,
                    productDetail.product.price,
                )
            ) { }
        }

        navigator.navigate(AppRoutes.Shopping)
    }

    override fun onAddToCartClick(productDetail: ProductDetail) {
        viewModelScope.launch {
            updateShoppingCartProductUseCase.execute(
                ShoppingCartProduct(
                    productDetail.product.name,
                    productDetail.product.price,
                )
            ) { }
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
                ProductDetailsViewModel(
                    product = product,
                    navigator = navigator
                )
            }
        }
    }
}