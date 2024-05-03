package com.rsddm.marketplace.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.Resource
import domain.entities.Theme
import domain.useCases.LoadAppThemeUseCase
import domain.useCases.LoadAppThemeUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SetupViewModel : ViewModel() {

    private val loadAppThemeUseCase: LoadAppThemeUseCase by LoadAppThemeUseCaseFactory()

    private val _state = MutableStateFlow<SetupState>(SetupState.Splash)
    val state: StateFlow<SetupState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            setupColor()
        }
    }

    private suspend fun setupColor() {
        loadAppThemeUseCase.execute("varejo") {
            _state.value = when (it) {
                is Resource.Error -> SetupState.Finish(
                    Theme(
                        primary = 0xFF000000,
                        onPrimary = 0xFFFFFFFF,
                        secondary = 0xFF000000,
                        onSecondary = 0xFFFFFFFF,
                        tertiary = 0xFF000000,
                        onTertiary = 0xFFFFFFFF,
                        background = 0xFFFFFFFF,
                        onBackground = 0xFF000000
                    )
                )

                is Resource.Success -> SetupState.Finish(it.data)
            }
        }
    }
}

sealed class SetupState {
    data object Splash : SetupState()
    data class Finish(val theme: Theme) : SetupState()
}