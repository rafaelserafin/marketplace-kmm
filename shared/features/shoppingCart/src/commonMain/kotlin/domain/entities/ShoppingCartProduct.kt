package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingCartProduct(
    val name: String,
    val price: String,
    val quantity: Int = 1
)