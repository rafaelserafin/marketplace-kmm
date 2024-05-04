package com.rsddm.marketplace.features.profile.login

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import kotlinx.coroutines.flow.MutableStateFlow

class ProfileLoginViewModel(navigator: Navigator) :
    BaseViewModel<ProfileLogin.UIState, ProfileLogin.ActionBundle>(navigator),
    ProfileLogin.ActionBundle {

    override val _uiState = MutableStateFlow<ProfileLogin.UIState>(ProfileLogin.UIState.Default)
    override val actionBundle: ProfileLogin.ActionBundle = this

    override fun login(username: String, password: String) {

    }

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
