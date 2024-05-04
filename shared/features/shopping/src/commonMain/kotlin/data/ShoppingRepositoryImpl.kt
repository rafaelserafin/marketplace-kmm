package data

import data.api.ShoppingApi
import data.api.errors.StockLackException
import data.api.errors.TransactionException
import data.api.errors.UnauthorizedException
import data.session.ShoppingCartSession
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import kotlinx.coroutines.delay
import session.Session

private const val ORDERS_KEY = "orders_"
private const val SHOPPING_CART_KEY = "shopping_cart_"

class ShoppingRepositoryImpl(
    private val api: ShoppingApi,
    private val localStorage: LocalStorage
) : ShoppingRepository {
    override suspend fun finalizePurchase(shoppingOrder: ShoppingOrder): ShoppingOrder {

        delay(2000)
        val moreThenTree = shoppingOrder.products.firstOrNull { it.quantity > 3 }

        when {
            !Session.isAuthenticated() -> throw UnauthorizedException()
            moreThenTree != null -> throw StockLackException(moreThenTree.name)
            shoppingOrder.products.sumOf { it.quantity } > 16 -> throw TransactionException()
            else -> return shoppingOrder.copy(
                deliveryForecast = "Oba, Você vai receber seu produto em 2 dias"
            )
        }
    }

    override suspend fun savePurchaseLocal(shoppingOrder: ShoppingOrder) {
        val combinedKey = ORDERS_KEY + Session.userToken

        val shoppingOrders = localStorage.get<MutableList<ShoppingOrder>>(combinedKey) ?: mutableListOf()
        shoppingOrders.add(shoppingOrder)

        localStorage.save(combinedKey, shoppingOrder)
    }

    override suspend fun getOrders() : List<ShoppingOrder> {
        val combinedKey = ORDERS_KEY + Session.userToken

        return localStorage.get<List<ShoppingOrder>>(combinedKey) ?: listOf()
    }

    override suspend fun saveShoppingCart(products: List<ShoppingCartProduct>) {
        val combinedKey = SHOPPING_CART_KEY + Session.userToken

        ShoppingCartSession.state.collect {
            localStorage.save(combinedKey, it)
        }
    }

    override suspend fun getShoppingCart(): List<ShoppingCartProduct> {
        val combinedKey = SHOPPING_CART_KEY + Session.userToken

        return localStorage.get<List<ShoppingCartProduct>>(combinedKey) ?: listOf()
    }
}