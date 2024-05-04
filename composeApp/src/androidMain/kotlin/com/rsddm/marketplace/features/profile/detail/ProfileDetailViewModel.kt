package com.rsddm.marketplace.features.profile.detail

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileDetailViewModel(navigator: Navigator) :
    BaseViewModel<ProfileDetail.UIState, ProfileDetail.ActionBundle>(navigator),
    ProfileDetail.ActionBundle {

    override val _uiState = MutableStateFlow<ProfileDetail.UIState>(ProfileDetail.UIState.Default)
    override val actionBundle: ProfileDetail.ActionBundle = this

    override fun setupTopBar(title: String) {
        navigator.setState(NavigatorState.Navigation(title))
    }

    companion object {
        fun provideFactory(
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ProfileDetailViewModel(
                    navigator = navigator
                )
            }
        }
    }

}
