package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val query: String,
    val offset: Int = 0,
    val limit: Int = 10
)
