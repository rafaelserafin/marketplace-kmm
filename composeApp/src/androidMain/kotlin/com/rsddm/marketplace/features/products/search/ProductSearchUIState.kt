package com.rsddm.marketplace.features.products.search

import domain.entities.Product

sealed class ProductSearchUIState {
    data object Loading: ProductSearchUIState()
    data class Searching(val products: List<Product>, val loadMore: Boolean = true) : ProductSearchUIState()
}