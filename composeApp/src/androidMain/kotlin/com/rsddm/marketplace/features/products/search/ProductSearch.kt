package com.rsddm.marketplace.features.products.search

import com.rsddm.marketplace.core.BaseActionBundle
import domain.entities.Product

class ProductSearch{
    sealed class UIState {
        data object Loading: UIState()
        data class Searching(val query: String, val products: List<Product>, val loadMore: Boolean = true) : UIState()
    }

    interface ActionBundle : BaseActionBundle {
        fun onProductClick(product: Product)
        fun search(query: String)
    }
}

