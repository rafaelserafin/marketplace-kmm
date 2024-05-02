package com.rsddm.marketplace.features.products.search

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.features.products.ProductsRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import domain.entities.Product
import domain.entities.Search
import domain.useCases.SearchProductsUseCase
import domain.useCases.SearchProductsUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductSearchViewModel(var query: String, navigator: Navigator) : BaseViewModel(navigator) {

    private var offset = 0
    private var endOfList = false
    private val searchProductsUseCase: SearchProductsUseCase by SearchProductsUseCaseFactory()

    private val _uiState: MutableStateFlow<ProductSearchUIState> =
        MutableStateFlow(ProductSearchUIState.Loading)
    val uiState: StateFlow<ProductSearchUIState> = _uiState

    init {
        navigator.setState(NavigatorState.Navigation(title = "Buscar produto"))

        search(query)
    }

    fun onProductClick(product: Product) {
        navigator.navigate(ProductsRoutes.Detail, product)
    }

    fun search(query: String) {
        val isLoadMore = this.query == query
        if (isLoadMore) {
            offset += 10
        } else {
            offset = 0
            this.query = query
            this.endOfList = false
            _uiState.value = ProductSearchUIState.Loading
        }

        if (endOfList) return

        viewModelScope.launch {
            searchProductsUseCase.execute(
                Search(
                    query = query,
                    offset = offset
                )
            ) {
                when (it) {
                    is Resource.Success -> if (uiState.value is ProductSearchUIState.Searching && isLoadMore) {
                        endOfList = it.data.size < 10

                        _uiState.emit(
                            ProductSearchUIState.Searching(
                                (uiState.value as ProductSearchUIState.Searching).products + it.data,
                                loadMore = !endOfList
                            )
                        )
                    } else {
                        _uiState.emit(ProductSearchUIState.Searching(it.data))
                    }

                    is Resource.Error -> _uiState.emit(ProductSearchUIState.Searching(listOf()))
                }
            }
        }
    }

    companion object {
        fun provideFactory(
            search: String,
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ProductSearchViewModel(
                    query = search,
                    navigator = navigator
                )
            }
        }
    }
}