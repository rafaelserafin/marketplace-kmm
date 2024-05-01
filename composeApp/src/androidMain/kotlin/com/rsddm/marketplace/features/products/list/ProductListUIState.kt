package com.rsddm.marketplace.features.products.list

import domain.entities.ProductsCategory

sealed class ProductListUIState {
    data object Loading : ProductListUIState()
    data class Error(val message: String) : ProductListUIState()
    data class Listing(val categories: List<ProductsCategory>) : ProductListUIState()
}