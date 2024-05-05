package data.api

import domain.entities.UserProfile

interface ProfileApi {
    suspend fun login(username: String, password: String) : UserProfile
}