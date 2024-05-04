package com.rsddm.marketplace.features.profile.login

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileLoginViewModel(navigator: Navigator) : BaseViewModel(navigator) {

    private val _state = MutableStateFlow<ProfileLoginUIState>(ProfileLoginUIState.Default)
    val state: StateFlow<ProfileLoginUIState> = _state.asStateFlow()

    override fun setupTopBar(title: String) {
        navigator.setState(NavigatorState.CleanNavigation(title))
    }

    companion object {
        fun provideFactory(
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ProfileLoginViewModel(
                    navigator = navigator
                )
            }
        }
    }

}
