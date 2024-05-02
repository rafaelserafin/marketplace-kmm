package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class ProductDetail(
    val product: Product,
    val installment: String? = null,
    val delivery: String? = null,
    val description: String? = null,
    val characteristics: List<ProductSpec>? = null,
    val relatedProducts: List<Product>? = null
)