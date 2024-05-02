package com.rsddm.marketplace.features.products.detail

import domain.entities.ProductDetail

sealed class ProductDetailUIState(open val productDetail: ProductDetail) {
    data class Loading(override val productDetail: ProductDetail) : ProductDetailUIState(productDetail)
    data class Detailed(override val productDetail: ProductDetail) : ProductDetailUIState(productDetail)
}