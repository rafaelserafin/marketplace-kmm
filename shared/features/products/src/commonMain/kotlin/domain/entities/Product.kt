package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val name: String,
    val shortDescription: String,
    val price: String,
    val oldPrice: String? = null
)