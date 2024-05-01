package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ProductsCategory(
    val category: String,
    val products: List<Product>
)