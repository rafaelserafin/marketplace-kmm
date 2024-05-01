package com.rsddm.marketplace.features.products.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.designSystem.components.Loading
import com.rsddm.marketplace.designSystem.components.OnSearch
import com.rsddm.marketplace.designSystem.components.ProductCarousel
import com.rsddm.marketplace.designSystem.components.SearchBar
import domain.entities.ProductsCategory

@Composable
fun ProductsHomeScreen(viewModel: ProductListViewModel) {

    val uiState = viewModel.uiState.collectAsState()

    when (uiState.value) {
        is ProductListUIState.Listing -> {
            List(
                (uiState.value as ProductListUIState.Listing).categories,
                onSearch = viewModel::onSearch
            )
        }

        is ProductListUIState.Error -> {}
        is ProductListUIState.Loading -> Loading()
    }
}

@Composable
private fun List(categories: List<ProductsCategory>, onSearch: OnSearch) {
    Column {
        SearchBar(
            hint = "Pesquisar",
            onSearch = onSearch,
            backgroundColor = MaterialTheme.colorScheme.primary,
            backgroundContrast = MaterialTheme.colorScheme.onPrimary
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(categories) { category ->
                ProductCarousel(category)
            }
        }
    }
}


