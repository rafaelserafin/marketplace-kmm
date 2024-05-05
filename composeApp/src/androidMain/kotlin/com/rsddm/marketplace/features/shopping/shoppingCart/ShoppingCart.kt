package com.rsddm.marketplace.features.shopping.shoppingCart

import com.rsddm.marketplace.core.BaseActionBundle
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder

class ShoppingCart {
    sealed class UIState {
        data object Empty : UIState()
        data object FinalizingPurchase : UIState()
        data class CheckIn(val products: List<ShoppingCartProduct>, val totalAmount: String) : UIState()
        data class OrderSuccess(val shoppingOrder: ShoppingOrder) : UIState()
        data class OrderError(val error: String, val actionName: String, val action: () -> Unit) : UIState()
    }

    interface ActionBundle : BaseActionBundle {
        fun updateProductQuantity(product: ShoppingCartProduct, quantity: Int)
        fun onBuyClick()
        fun goToMyOrders()
    }
}
