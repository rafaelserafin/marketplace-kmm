package com.rsddm.ecommerce.designSystem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

data class ProductCarouselItem(
    val title: String,
    val imageUrl: String,
    val lead: String,
    val price: String,
    val oldPrice: String? = null
)

@Composable
fun ProductCarousel(products: List<ProductCarouselItem>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(products) {
            ProductCarouselItem(it)
        }
    }

}

@Composable
private fun ProductCarouselItem(product: ProductCarouselItem) {

}