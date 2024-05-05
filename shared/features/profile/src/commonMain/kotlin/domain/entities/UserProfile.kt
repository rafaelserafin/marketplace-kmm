package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name: String,
    val email: String,
    val birthday: String,
    val token: String
)
