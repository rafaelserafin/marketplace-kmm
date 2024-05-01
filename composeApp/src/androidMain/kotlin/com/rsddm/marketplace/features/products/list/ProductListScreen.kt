package com.rsddm.marketplace.features.products.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.designSystem.components.ProductCarousel
import domain.entities.ProductsCategory

@Composable
fun ProductsHomeScreen(viewModel: ProductListViewModel) {

    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        is ProductListUIState.Listing -> {
            List((uiState.value as ProductListUIState.Listing).categories)
        }

        is ProductListUIState.Error -> {}
        is ProductListUIState.Loading -> {
            Loading()
        }
    }
}

@Composable
private fun List(categories: List<ProductsCategory>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(categories) { category ->
            ProductCarousel(category)
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

