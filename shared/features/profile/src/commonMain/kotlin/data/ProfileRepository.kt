package data

import domain.entities.UserLogin
import domain.entities.UserProfile

interface ProfileRepository {
    suspend fun login(userLogin: UserLogin) : UserProfile

    suspend fun saveUserSession(userProfile: UserProfile)

    suspend fun deleteUserSession()

    suspend fun getUserProfile() : UserProfile?
}