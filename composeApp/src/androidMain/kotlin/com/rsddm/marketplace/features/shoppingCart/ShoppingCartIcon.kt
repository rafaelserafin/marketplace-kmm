package com.rsddm.marketplace.features.shoppingCart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ShoppingCartIcon(viewModel: ShoppingCartIconViewModel) {
    Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.TopEnd) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Carrinho de Compras",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(10.dp)
                .size(28.dp)
        )

        Text(
            text = "5",
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .size(18.dp)
                .background(MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(16.dp))
        )

    }
}

@Preview
@Composable
fun Preview() {
    Box(
        modifier = Modifier.size(120.dp).background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        ShoppingCartIcon(viewModel = viewModel())

    }
}