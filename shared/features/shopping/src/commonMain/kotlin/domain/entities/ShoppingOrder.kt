package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingOrder(
    val products: List<ShoppingCartProduct>,
    val totalAmount: String,
    val paymentDescription: String,
    val deliveryForecast: String,
    val createdAt: Long
)
