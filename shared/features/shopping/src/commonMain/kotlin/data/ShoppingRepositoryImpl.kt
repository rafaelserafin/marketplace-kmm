package data

import Provider
import bridge.ModuleBridge
import common.errors.UnauthorizedException
import data.api.ShoppingApi
import data.api.ShoppingApiImpl
import data.api.errors.StockLackException
import data.api.errors.TransactionException
import data.session.ShoppingCartSession
import di.CoreContainer
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

private const val ORDERS_KEY = "orders_"
private const val SHOPPING_CART_KEY = "shopping_cart_"

class ShoppingRepositoryProvider: Provider<ShoppingRepository>() {
    override fun provide(): ShoppingRepository =
        ShoppingRepositoryImpl(
            CoreContainer.localStorage
        )
}

class ShoppingRepositoryImpl(
    private val localStorage: LocalStorage
) : ShoppingRepository {
    override suspend fun finalizePurchase(shoppingOrder: ShoppingOrder): ShoppingOrder {

        delay(2000)
        val moreThenTree = shoppingOrder.products.firstOrNull { it.quantity > 3 }

        when {
            ModuleBridge.profile?.isUserAuthenticated() != true -> throw UnauthorizedException()
            moreThenTree != null -> throw StockLackException(moreThenTree.name)
            shoppingOrder.products.sumOf { it.quantity } > 16 -> throw TransactionException()
            else -> return shoppingOrder.copy(
                deliveryForecast = "Oba, Você vai receber seu produto em 2 dias"
            )
        }
    }

    override suspend fun savePurchaseLocal(shoppingOrder: ShoppingOrder) {
        val combinedKey = ORDERS_KEY + ModuleBridge.profile?.getUserSessionToken()

        val shoppingOrders = localStorage.get<MutableList<ShoppingOrder>>(combinedKey) ?: mutableListOf()
        shoppingOrders.add(shoppingOrder)

        localStorage.save(combinedKey, shoppingOrders)
    }

    override suspend fun getOrders() : List<ShoppingOrder> {
        val combinedKey = ORDERS_KEY + ModuleBridge.profile?.getUserSessionToken()

        return localStorage.get<List<ShoppingOrder>>(combinedKey) ?: listOf()
    }

    override suspend fun saveShoppingCart(products: List<ShoppingCartProduct>) {
        val combinedKey = SHOPPING_CART_KEY + ModuleBridge.profile?.getUserSessionToken()

        val items = ShoppingCartSession.state.first()
        localStorage.save(combinedKey, items)
    }

    override suspend fun getShoppingCart(): List<ShoppingCartProduct> {
        val combinedKey = SHOPPING_CART_KEY + ModuleBridge.profile?.getUserSessionToken()

        return localStorage.get<List<ShoppingCartProduct>>(combinedKey) ?: listOf()
    }
}