package com.rsddm.marketplace.features.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState.Loading)
    val uiState: StateFlow<HomeUIState> = _uiState
}