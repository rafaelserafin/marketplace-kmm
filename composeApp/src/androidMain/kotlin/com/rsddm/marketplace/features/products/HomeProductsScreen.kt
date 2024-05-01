package com.rsddm.marketplace.features.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.entities.ProductsCategory

@Composable
fun HomeScreen(viewModel: HomeProductsViewModel) {

    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        is HomeProductsUIState.Listing -> {
            List((uiState.value as HomeProductsUIState.Listing).categories)
        }

        is HomeProductsUIState.Error -> {}
        is HomeProductsUIState.Loading -> {
            Loading()
        }
    }
}

@Composable
private fun List(categories: List<ProductsCategory>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(categories) { category ->
            Text(text = category.category)
            LazyRow {
                items(category.products) { product ->
                    Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
                        Text(product.name)
                    }
                }
            }
        }

    }
}

@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

