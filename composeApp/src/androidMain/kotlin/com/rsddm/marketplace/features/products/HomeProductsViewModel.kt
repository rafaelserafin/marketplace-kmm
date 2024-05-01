package com.rsddm.marketplace.features.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.Resource
import domain.useCases.LoadHomeProductsUseCase
import domain.useCases.LoadHomeProductsUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeProductsViewModel : ViewModel() {

    private val loadHomeProductsUseCase: LoadHomeProductsUseCase by LoadHomeProductsUseCaseFactory()

    private val _uiState: MutableStateFlow<HomeProductsUIState> =
        MutableStateFlow(HomeProductsUIState.Loading)
    val uiState: StateFlow<HomeProductsUIState> = _uiState

    init {
        viewModelScope.launch {
            loadHomeProductsUseCase.execute(Unit) {
                when (it) {
                    is Resource.Success -> _uiState.emit(HomeProductsUIState.Listing(it.data))
                    is Resource.Error -> _uiState.emit(
                        HomeProductsUIState.Error(
                            it.exception?.message ?: ""
                        )
                    )
                }
            }
        }
    }
}