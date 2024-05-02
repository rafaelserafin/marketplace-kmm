package domain.entities

import kotlinx.serialization.Serializable

@Serializable
class ProductSpec(
    val name: String,
    val value: String
)