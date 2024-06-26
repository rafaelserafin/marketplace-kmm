package com.rsddm.marketplace.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import common.Resource
import domain.entities.Theme
import domain.useCases.LoadAppThemeUseCase
import domain.useCases.LoadAppThemeUseCaseProvider
import domain.useCases.LoadShoppingCartUseCase
import domain.useCases.LoadShoppingCartUseCaseProvider
import domain.useCases.RefreshUserSessionUseCase
import domain.useCases.RefreshUserSessionUseCaseProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SetupViewModel : ViewModel() {

    private val loadAppThemeUseCase: LoadAppThemeUseCase by LoadAppThemeUseCaseProvider()
    private val refreshUserSessionUseCase: RefreshUserSessionUseCase by RefreshUserSessionUseCaseProvider()
    private val loadShoppingCartUseCase: LoadShoppingCartUseCase by LoadShoppingCartUseCaseProvider()

    private val _state = MutableStateFlow<SetupState>(SetupState.Splash)
    val state: StateFlow<SetupState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            setupColor()
        }
    }

    private suspend fun setupColor() {
        delay(1500)

        combine(
            loadAppThemeUseCase.execute("varejo"),
            refreshUserSessionUseCase.execute(Unit),
        ) { themeResource, _ ->
            loadShoppingCartUseCase.execute(Unit).collect()

            _state.value = when (themeResource) {
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

                is Resource.Success -> SetupState.Finish(themeResource.data)
            }
        }.collect()
    }
}

sealed class SetupState {
    data object Splash : SetupState()
    data class Finish(val theme: Theme) : SetupState()
}