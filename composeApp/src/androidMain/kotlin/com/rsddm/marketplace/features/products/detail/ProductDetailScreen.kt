package com.rsddm.marketplace.features.products.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.R
import com.rsddm.marketplace.designSystem.components.LineThroughText
import com.rsddm.marketplace.designSystem.components.Loading
import com.rsddm.marketplace.designSystem.components.OnProductClick
import com.rsddm.marketplace.designSystem.components.PhotoGallery
import com.rsddm.marketplace.designSystem.components.ProductCarousel
import domain.entities.ProductDetail
import domain.entities.ProductSpec
import domain.entities.ProductsCategory

typealias OnProductDetailClick = (ProductDetail) -> Unit

@Composable
fun ProductDetailScreen(
    state: ProductDetailUIState,
    onBuyClick: OnProductDetailClick,
    onAddToCartClick: OnProductDetailClick,
    onProductClick: OnProductClick
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp)
    ) {
        item {
            Text(
                state.productDetail.product.name,
                style = MaterialTheme.typography.titleMedium
            )
        }

        item {
            PhotoGallery(
                listOf(
                    R.drawable.temp_product,
                    R.drawable.temp_product,
                    R.drawable.temp_product,
                    R.drawable.temp_product,
                )
            )
        }

        item {
            ProductMetaInfo(state.productDetail)
        }

        when (state) {
            is ProductDetailUIState.Loading -> item {
                Loading(
                    modifier = Modifier.fillMaxWidth()
                        .height(220.dp)
                )
            }

            is ProductDetailUIState.Detailed -> item {
                ProductBody(
                    state.productDetail,
                    onBuyClick,
                    onAddToCartClick,
                    onProductClick
                )
            }
        }
    }
}

@Composable
private fun ProductMetaInfo(productDetail: ProductDetail) {
    Spacer(modifier = Modifier.height(8.dp))

    productDetail.product.oldPrice?.let {
        LineThroughText(
            text = it,
            style = MaterialTheme.typography.titleMedium
        )
    }

    Text(
        productDetail.product.price,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )

    productDetail.installment?.let { installment ->
        Text(
            installment,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        productDetail.product.shortDescription,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
private fun ProductBody(
    productDetail: ProductDetail,
    onBuyClick: OnProductDetailClick,
    onAddToCartClick: OnProductDetailClick,
    onProductClick: OnProductClick
) {

    Spacer(modifier = Modifier.height(16.dp))
    productDetail.delivery?.let { delivery ->
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Entrega",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = delivery,
            style = MaterialTheme.typography.bodyMedium
        )
    }

    Spacer(modifier = Modifier.height(32.dp))
    Button(
        onClick = { onBuyClick(productDetail) },
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(stringResource(R.string.buy))
    }

    Button(
        onClick = { onAddToCartClick(productDetail) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        )

    ) {
        Text(stringResource(R.string.add_to_cart))
    }

    Spacer(modifier = Modifier.height(32.dp))
    productDetail.characteristics?.let {
        ProductCharacteristics(it)
    }

    Spacer(modifier = Modifier.height(32.dp))
    productDetail.description?.let {
        Description(it)
    }

    Spacer(modifier = Modifier.height(32.dp))
    productDetail.relatedProducts?.let {
        ProductCarousel(
            category = ProductsCategory(
                category = stringResource(R.string.related_products),
                products = it
            ),
            onProductClick = onProductClick,
            horizontalPadding = 0.dp
        )
    }
}

@Composable
private fun ProductCharacteristics(list: List<ProductSpec>) {
    Text(
        text = "Caracteristicas",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
    )

    HorizontalDivider(modifier = Modifier.fillMaxWidth())

    list.forEachIndexed { index, item ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = if (index % 2 == 0) Color.White else Color.Gray)
                .padding(8.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxHeight()
                    .weight(.7f)
            )
            Text(
                text = item.value,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun Description(text: String) {
    val maxLines = remember { mutableIntStateOf(5) }

    Text(
        text = "Descrição",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
    )
    HorizontalDivider(modifier = Modifier.fillMaxWidth())

    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = maxLines.intValue
    )

    Text(
        text = if (maxLines.intValue > 5) "ver menos" else "ver mais",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.clickable {
            if (maxLines.intValue > 5) maxLines.intValue = 5 else maxLines.intValue = Int.MAX_VALUE
        }
    )
}