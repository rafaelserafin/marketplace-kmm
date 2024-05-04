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
import kotlinx.coroutines.launch

class ProductSearchViewModel(var query: String, navigator: Navigator) :
    BaseViewModel<ProductSearch.UIState, ProductSearch.ActionBundle>(navigator),
    ProductSearch.ActionBundle {

    private var offset = 0
    private var endOfList = false
    private val searchProductsUseCase: SearchProductsUseCase by SearchProductsUseCaseFactory()

    override val _uiState: MutableStateFlow<ProductSearch.UIState> =
        MutableStateFlow(ProductSearch.UIState.Loading)
    override val actionBundle: ProductSearch.ActionBundle = this

    init {
        search(query)
    }

    override fun onProductClick(product: Product) {
        navigator.navigate(ProductsRoutes.Detail, product)
    }

    override fun search(query: String) {
        val isLoadMore = this.query == query
        if (isLoadMore) {
            offset += 10
        } else {
            offset = 0
            this.query = query
            this.endOfList = false
            setUIState(ProductSearch.UIState.Loading)
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
                    is Resource.Success -> if (uiState.value is ProductSearch.UIState.Searching && isLoadMore) {
                        endOfList = it.data.size < 10

                        setUIState(
                            ProductSearch.UIState.Searching(
                                query,
                                (uiState.value as ProductSearch.UIState.Searching).products + it.data,
                                loadMore = !endOfList
                            )
                        )
                    } else {
                        setUIState(ProductSearch.UIState.Searching(query, it.data))
                    }

                    is Resource.Error -> setUIState(
                        ProductSearch.UIState.Searching(
                            query,
                            listOf()
                        )
                    )
                }
            }
        }
    }

    override fun setupTopBar(title: String) {
        navigator.setState(NavigatorState.Navigation(title = title))
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