package com.rsddm.marketplace.features.shoppingCart.shoppingCart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.rsddm.marketplace.R

@Composable
fun ShoppingCartScreen(viewModel: ShoppingCartViewModel) {

    val title = stringResource(R.string.shopping_cart)
    LaunchedEffect(true) {
        viewModel.setupTopBar(title)
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.Red)
    ) {

    }
}