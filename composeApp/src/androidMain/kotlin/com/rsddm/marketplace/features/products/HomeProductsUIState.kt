package com.rsddm.marketplace.features.products

import domain.entities.ProductsCategory

sealed class HomeProductsUIState {
    data object Loading: HomeProductsUIState()
    data class Listing(val categories: List<ProductsCategory>): HomeProductsUIState()
    data object Error: HomeProductsUIState()
}