package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingCartProduct(
    val name: String,
    val price: String,
    var quantity: Int = 1
)