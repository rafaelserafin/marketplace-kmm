package com.rsddm.marketplace.features.shoppingCart.shoppingCart

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState

class ShoppingCartViewModel(navigator: Navigator) : BaseViewModel(navigator) {

    override fun setupTopBar(title: String) {
        navigator.setState(navigatorState = NavigatorState.CleanNavigation(title))
    }

    companion object {
        fun provideFactory(
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ShoppingCartViewModel(
                    navigator = navigator
                )
            }
        }
    }
}