package com.rsddm.marketplace.features.shoppingCart.icon

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rsddm.marketplace.core.BaseViewModel
import com.rsddm.marketplace.features.products.detail.ProductDetailViewModel
import com.rsddm.marketplace.features.shoppingCart.ShoppingCartRoutes
import com.rsddm.marketplace.navigation.Navigator
import data.session.ShoppingCartSession
import domain.entities.Product
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

    override fun setupTopBar() {

    }
}