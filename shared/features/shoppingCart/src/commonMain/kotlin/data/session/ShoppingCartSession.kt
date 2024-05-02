package data.session

import domain.entities.ShoppingCartProduct
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

object ShoppingCartSession {
    private val _state = MutableSharedFlow<List<ShoppingCartProduct>>(10)
    val state: SharedFlow<List<ShoppingCartProduct>> = _state.asSharedFlow()

    internal fun remove(shoppingCartProduct: ShoppingCartProduct) {

    }

    suspend fun add(shoppingCartProduct: ShoppingCartProduct) {
        val products =
            if (_state.replayCache.isEmpty()) mutableListOf() else _state.replayCache.last().toMutableList()
        val product = products.firstOrNull { item -> item.name == shoppingCartProduct.name }

        if (product != null) {
            products.remove(product)
            products.add(
                product.copy(
                    quantity = product.quantity + 1
                )
            )
        } else {
            products.add(shoppingCartProduct)
        }

        _state.tryEmit(products)
    }
}