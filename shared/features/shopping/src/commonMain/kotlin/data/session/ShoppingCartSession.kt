package data.session

import domain.entities.ShoppingCartProduct
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

object ShoppingCartSession {

    private val _state = MutableSharedFlow<List<ShoppingCartProduct>>(1)
    val state: SharedFlow<List<ShoppingCartProduct>> = _state.asSharedFlow()

    internal fun remove(shoppingCartProduct: ShoppingCartProduct): List<ShoppingCartProduct> {
        val products =
            if (_state.replayCache.isEmpty()) mutableListOf() else _state.replayCache.last()
                .toMutableList()
        val product = products.firstOrNull { item -> item.name == shoppingCartProduct.name }

        if (product != null) {
            product.quantity += shoppingCartProduct.quantity

            if (product.quantity <= 0) {
                products.remove(product)
            }
        }

        _state.tryEmit(products)
        return products
    }

    internal fun removeAll(){
        _state.tryEmit(listOf())
    }

    internal fun add(shoppingCartProduct: ShoppingCartProduct): List<ShoppingCartProduct> {
        val products =
            if (_state.replayCache.isEmpty()) mutableListOf() else _state.replayCache.last()
                .toMutableList()
        val product = products.firstOrNull { item -> item.name == shoppingCartProduct.name }

        if (product != null) {
            product.quantity += shoppingCartProduct.quantity
        } else {
            products.add(shoppingCartProduct)
        }

        _state.tryEmit(products)
        return products
    }

    internal fun addAll(products: List<ShoppingCartProduct>) {
        _state.tryEmit(products)
    }
}