package com.rsddm.ecommerce.features.home

sealed class HomeUIState {
    data object Loading: HomeUIState()
    data object Listing: HomeUIState()
    data object Error: HomeUIState()
}