package com.rsddm.marketplace.features.products.list

import com.rsddm.marketplace.core.BaseActionBundle
import domain.entities.Product
import domain.entities.ProductsCategory

class ProductList {
    sealed class UIState {
        data object Loading : UIState()
        data class Error(val message: String) : UIState()
        data class Listing(val categories: List<ProductsCategory>) : UIState()
    }

    interface ActionBundle : BaseActionBundle {
        fun onProductClick(product: Product)
        fun onSearch(text: String)
    }
}

