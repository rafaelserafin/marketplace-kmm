package com.rsddm.marketplace.features.products.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rsddm.marketplace.R
import com.rsddm.marketplace.designSystem.components.LineThroughText
import com.rsddm.marketplace.designSystem.components.Loading
import com.rsddm.marketplace.designSystem.components.OnProductClick
import com.rsddm.marketplace.designSystem.components.OnSearch
import com.rsddm.marketplace.designSystem.components.SearchBar
import domain.entities.Product

@Composable
fun ProductSearchScreen(viewModel: ProductSearchViewModel) {
    LaunchedEffect(true) {
        viewModel.setupTopBar()
    }

    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.value) {
        is ProductSearchUIState.Loading -> Loading()
        is ProductSearchUIState.Searching -> Searching(
            viewModel.query,
            (uiState.value as ProductSearchUIState.Searching),
            onSearch = viewModel::search,
            onProductClick = viewModel::onProductClick
        )
    }
}

@Composable
private fun Searching(text: String, searching: ProductSearchUIState.Searching, onSearch: OnSearch, onProductClick: OnProductClick) {

    val listState = rememberLazyListState()

    val reachedBottom: Boolean by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) onSearch(text)
    }

    Column {
        SearchBar(
            initialText = text,
            hint = "Pesquisar",
            onSearch = onSearch
        )

        LazyColumn(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = listState
        ) {
            items(searching.products) {
                Product(it, onProductClick)
            }

            if (searching.loadMore) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun Product(product: Product, onProductClick: OnProductClick) {
    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.clickable {
            onProductClick(product)
        }
    ) {
        Row(
            modifier = Modifier.height(120.dp)
                .padding(8.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.temp_product),
                    contentDescription = "User Logo",
                    modifier = Modifier
                        .size(70.dp)
                        .background(MaterialTheme.colorScheme.background)
                )
            }

            Column {
                Text(product.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    product.shortDescription,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium
                )
                product.oldPrice?.let { it1 ->
                    LineThroughText(
                        text = it1,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text(product.price, style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}