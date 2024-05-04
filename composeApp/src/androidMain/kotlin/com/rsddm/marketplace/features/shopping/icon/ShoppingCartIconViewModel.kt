package com.rsddm.marketplace.features.shopping.icon

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.features.shopping.ShoppingCartRoutes
import com.rsddm.marketplace.navigation.Navigator
import data.session.ShoppingCartSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartIconViewModel(navigator: Navigator) : BaseViewModel(navigator) {

    private val _badgeCount = MutableStateFlow(0)
    val badgeCount: StateFlow<Int> = _badgeCount.asStateFlow()

    init {
        viewModelScope.launch {
            ShoppingCartSession.state.collect {
                _badgeCount.value = it.size
            }
        }
    }

    fun onShoppingCartClick() {
        navigator.navigate(ShoppingCartRoutes.ShoppingCartProducts)
    }

    override fun setupTopBar(title: String) { }

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