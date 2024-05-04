package com.rsddm.marketplace.core

import androidx.lifecycle.ViewModel
import com.rsddm.marketplace.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<S, B : BaseActionBundle>(val navigator: Navigator) : ViewModel() {

    protected abstract val _uiState: MutableStateFlow<S>
    val uiState: StateFlow<S> by lazy { _uiState.asStateFlow()  }

    abstract val actionBundle: B

    fun setUIState(uiState: S) {
        _uiState.value = uiState
    }
}