package data

import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder

interface ShoppingRepository {
    suspend fun finalizePurchase(shoppingOrder: ShoppingOrder) : ShoppingOrder

    suspend fun savePurchaseLocal(shoppingOrder: ShoppingOrder)

    suspend fun getOrders() : List<ShoppingOrder>

    suspend fun saveShoppingCart(products: List<ShoppingCartProduct>)

    suspend fun getShoppingCart() : List<ShoppingCartProduct>
}