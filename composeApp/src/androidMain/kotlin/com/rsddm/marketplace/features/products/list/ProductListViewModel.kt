package com.rsddm.marketplace.features.products.list

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.R
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.features.products.ProductsRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import domain.entities.Product
import domain.useCases.LoadHomeProductsUseCase
import domain.useCases.LoadHomeProductsUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(navigator: Navigator) :
    BaseViewModel<ProductList.UIState, ProductList.ActionBundle>(navigator),
    ProductList.ActionBundle{

    private val loadHomeProductsUseCase: LoadHomeProductsUseCase by LoadHomeProductsUseCaseProvider()

    override val _uiState: MutableStateFlow<ProductList.UIState> = MutableStateFlow(ProductList.UIState.Loading)
    override val actionBundle: ProductList.ActionBundle = this

    init {
        viewModelScope.launch {
            loadHomeProductsUseCase.execute(Unit) {
                when (it) {
                    is Resource.Success -> setUIState(ProductList.UIState.Listing(it.data))
                    is Resource.Error -> setUIState(
                        ProductList.UIState.Error(
                            it.exception?.message ?: ""
                        )
                    )
                }
            }
        }
    }

    override fun onProductClick(product: Product) {
        navigator.navigate(ProductsRoutes.Detail, product)
    }

    override fun onSearch(text: String) {
        navigator.navigate(ProductsRoutes.Search, text)
    }

    override fun setupTopBar(title: String) {
        navigator.setState(NavigatorState.Home("Olá, Rafael Serafin", R.drawable.user_logo))
    }

    companion object {
        fun provideFactory(
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ProductListViewModel(
                    navigator = navigator
                )
            }
        }
    }
}
