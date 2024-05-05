package com.rsddm.marketplace.features.profile.login

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import domain.entities.UserLogin
import domain.useCases.LoginUseCase
import domain.useCases.LoginUseCaseFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileLoginViewModel(navigator: Navigator) :
    BaseViewModel<ProfileLogin.UIState, ProfileLogin.ActionBundle>(navigator),
    ProfileLogin.ActionBundle {

    private val loginUseCase: LoginUseCase by LoginUseCaseFactory()

    override val _uiState = MutableStateFlow<ProfileLogin.UIState>(ProfileLogin.UIState.Idle)
    override val actionBundle: ProfileLogin.ActionBundle = this

    private var userLogin: UserLogin? = null

    override fun login(username: String, password: String) {
        setUIState(ProfileLogin.UIState.Loading)

        userLogin = UserLogin(username, password)

        viewModelScope.launch {
            userLogin?.let { user ->
                loginUseCase.execute(user) {
                    when(it) {
                        is Resource.Error -> setUIState(ProfileLogin.UIState.Error(user, it.exception?.message))
                        is Resource.Success -> navigator.popBackStack()
                    }
                }
            }
        }
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
