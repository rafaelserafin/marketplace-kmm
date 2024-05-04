package com.rsddm.marketplace.features.shopping.icon

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.features.shopping.ShoppingCartRoutes
import com.rsddm.marketplace.navigation.Navigator
import data.session.ShoppingCartSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ShoppingCartIconViewModel(navigator: Navigator) :
    BaseViewModel<ShoppingCartIcon.UIState, ShoppingCartIcon.ActionBundle>(navigator),
    ShoppingCartIcon.ActionBundle {

    override val _uiState: MutableStateFlow<ShoppingCartIcon.UIState> = MutableStateFlow(
        ShoppingCartIcon.UIState.Badge(0)
    )
    override val actionBundle: ShoppingCartIcon.ActionBundle = this

    init {
        viewModelScope.launch {
            ShoppingCartSession.state.collect {
                setUIState(ShoppingCartIcon.UIState.Badge(it.size))
            }
        }
    }

    override fun onShoppingCartClick() {
        navigator.navigate(ShoppingCartRoutes.ShoppingCartProducts)
    }

    override fun setupTopBar(title: String) { /* Do nothing here */ }

    companion object {
        fun provideFactory(
            navigator: Navigator,
        ) = viewModelFactory {
            initializer {
                ShoppingCartIconViewModel(
                    navigator = navigator
                )
            }
        }
    }

}