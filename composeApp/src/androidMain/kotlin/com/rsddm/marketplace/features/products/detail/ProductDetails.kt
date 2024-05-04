package com.rsddm.marketplace.features.products.detail

import com.rsddm.marketplace.core.BaseActionBundle
import domain.entities.Product
import domain.entities.ProductDetail

class ProductDetails {
    sealed class UIState(open val productDetail: ProductDetail) {
        class Loading(productDetail: ProductDetail) : UIState(productDetail)
        class Detailed(productDetail: ProductDetail) : UIState(productDetail)
    }

    interface ActionBundle : BaseActionBundle {
        fun onProductClick(product: Product)
        fun onBuyClick(productDetail: ProductDetail)
        fun onAddToCartClick(productDetail: ProductDetail)
    }
}