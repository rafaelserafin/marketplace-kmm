package com.rsddm.ecommerce.features.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    viewModel.uiState.collectAsState()

}

