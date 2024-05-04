package com.rsddm.marketplace.features.shopping.shoppingCart

import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder

sealed class ShoppingCartUIState {
    data object Empty : ShoppingCartUIState()
    data object FinalizingPurchase : ShoppingCartUIState()
    data class CheckIn(val products: List<ShoppingCartProduct>, val totalAmount: String) : ShoppingCartUIState()
    data class OrderSuccess(val shoppingOrder: ShoppingOrder) : ShoppingCartUIState()
    data class OrderError(val error: String, val actionName: String, val action: () -> Unit) : ShoppingCartUIState()
}