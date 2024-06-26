package com.rsddm.marketplace.designSystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.R
import domain.entities.Product
import domain.entities.ProductsCategory

typealias OnProductClick = (Product) -> Unit

@Composable
fun ProductCarousel(
    category: ProductsCategory,
    onProductClick: OnProductClick,
    horizontalPadding: Dp = 16.dp
) {

    Column {
        Text(
            text = category.category,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = horizontalPadding, end = horizontalPadding)
        ) {
            items(category.products) {
                ProductCarouselItem(it, onProductClick)
            }
        }
    }
}

@Composable
private fun ProductCarouselItem(product: Product, onProductClick: OnProductClick) {

    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.clickable {
            onProductClick(product)
        }
    ) {
        Column(
            modifier = Modifier.width(120.dp)
                .height(170.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.temp_product),
                contentDescription = "User Logo",
                modifier = Modifier
                    .size(70.dp)
                    .background(MaterialTheme.colorScheme.background)
            )

            Text(
                text = product.name,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
            )

            product.oldPrice?.let {

                LineThroughText(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text(
                text = product.price,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }


}