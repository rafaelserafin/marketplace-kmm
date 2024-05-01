package com.rsddm.marketplace.features.products.list

import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.R
import com.rsddm.marketplace.features.products.ProductsRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import domain.entities.Product
import domain.useCases.LoadHomeProductsUseCase
import domain.useCases.LoadHomeProductsUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val navigator: Navigator
) : ViewModel() {

    private val loadHomeProductsUseCase: LoadHomeProductsUseCase by LoadHomeProductsUseCaseFactory()

    private val _uiState: MutableStateFlow<ProductListUIState> =
        MutableStateFlow(ProductListUIState.Loading)
    val uiState: StateFlow<ProductListUIState> = _uiState

    init {
        navigator.setState(NavigatorState.Home("Olá, Rafael Serafin", R.drawable.user_logo) )

        viewModelScope.launch {
            loadHomeProductsUseCase.execute(Unit) {
                when (it) {
                    is Resource.Success -> _uiState.emit(ProductListUIState.Listing(it.data))
                    is Resource.Error -> _uiState.emit(
                        ProductListUIState.Error(
                            it.exception?.message ?: ""
                        )
                    )
                }
            }
        }
    }

    fun onProductClick(product: Product) {
        navigator.navigate(ProductsRoutes.Detail)
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
