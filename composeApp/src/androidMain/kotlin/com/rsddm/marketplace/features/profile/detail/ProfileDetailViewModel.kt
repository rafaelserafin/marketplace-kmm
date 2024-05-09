package com.rsddm.marketplace.features.profile.detail

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import common.Resource
import data.session.ShoppingCartSession
import domain.useCases.CleanSHoppingCartUseCase
import domain.useCases.CleanSHoppingCartUseCaseProvider
import domain.useCases.GetShoppingOrdersUseCase
import domain.useCases.GetShoppingOrdersUseCaseProvider
import domain.useCases.GetUserUseCase
import domain.useCases.GetUserUseCaseProvider
import domain.useCases.LogoutUseCase
import domain.useCases.LogoutUseCaseProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileDetailViewModel(navigator: Navigator) :
    BaseViewModel<ProfileDetail.UIState, ProfileDetail.ActionBundle>(navigator),
    ProfileDetail.ActionBundle {

    private val logoutUseCase: LogoutUseCase by LogoutUseCaseProvider()
    private val getUserUseCase: GetUserUseCase by GetUserUseCaseProvider()
    private val getShoppingOrdersUseCase: GetShoppingOrdersUseCase by GetShoppingOrdersUseCaseProvider()
    private val cleanShoppingOrdersUseCase: CleanSHoppingCartUseCase by CleanSHoppingCartUseCaseProvider()

    override val _uiState = MutableStateFlow<ProfileDetail.UIState>(ProfileDetail.UIState.Loading)
    override val actionBundle: ProfileDetail.ActionBundle = this

    init {
        viewModelScope.launch {
            getUserUseCase.execute(Unit) { user ->
                when (user) {
                    is Resource.Error -> navigator.popBackStack()
                    is Resource.Success -> {
                        getShoppingOrdersUseCase.execute(Unit) { order ->
                            when (order) {
                                is Resource.Error -> setUIState(
                                    ProfileDetail.UIState.Detail(
                                        user.data,
                                        listOf()
                                    )
                                )

                                is Resource.Success -> setUIState(
                                    ProfileDetail.UIState.Detail(
                                        user.data,
                                        order.data
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun logout() {
        setUIState(ProfileDetail.UIState.Loading)
        viewModelScope.launch {
            logoutUseCase.execute(Unit) {
                cleanShoppingOrdersUseCase.execute(Unit).collect()

                if (it.isSuccess) {
                    navigator.navigateBack()//navigator.popBackStack()
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
                ProfileDetailViewModel(
                    navigator = navigator
                )
            }
        }
    }
}
