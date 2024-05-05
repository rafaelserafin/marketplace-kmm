package com.rsddm.marketplace.features.products.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rsddm.marketplace.R
import com.rsddm.marketplace.designSystem.components.Loading
import com.rsddm.marketplace.designSystem.components.OnProductClick
import com.rsddm.marketplace.designSystem.components.OnSearch
import com.rsddm.marketplace.designSystem.components.ProductCarousel
import com.rsddm.marketplace.designSystem.components.SearchBar
import domain.entities.ProductsCategory

@Composable
fun ProductsHomeScreen(
    uiState: ProductList.UIState,
    actionBundle: ProductList.ActionBundle
) {
    LaunchedEffect(true) {
        actionBundle.setupTopBar("")
    }

    when (uiState) {
        is ProductList.UIState.Listing -> {
            List(
                uiState.categories,
                onSearch = actionBundle::onSearch,
                onProductClick = actionBundle::onProductClick
            )
        }

        is ProductList.UIState.Error -> {}
        is ProductList.UIState.Loading -> Loading()
    }
}

@Composable
private fun List(
    categories: List<ProductsCategory>,
    onSearch: OnSearch,
    onProductClick: OnProductClick
) {
    Column {
        SearchBar(
            hint = stringResource(R.string.search),
            onSearch = onSearch,
            backgroundColor = MaterialTheme.colorScheme.primary,
            backgroundContrast = MaterialTheme.colorScheme.onPrimary
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(categories) { category ->
                ProductCarousel(category, onProductClick)
            }
        }
    }
}


