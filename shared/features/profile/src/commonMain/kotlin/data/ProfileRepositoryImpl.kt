package data

import data.api.ProfileApi
import domain.entities.UserLogin
import domain.entities.UserProfile

private const val LOCAL_USER_KEY = "user_session"

class ProfileRepositoryImpl(
    private val api: ProfileApi,
    private val localStorage: LocalStorage
) : ProfileRepository {
    override suspend fun login(userLogin: UserLogin): UserProfile {
        return api.login(userLogin.username, userLogin.password)
    }

    override suspend fun saveUserSession(userProfile: UserProfile) {
        localStorage.save(LOCAL_USER_KEY, userProfile)
    }

    override suspend fun deleteUserSession() {
        localStorage.delete(LOCAL_USER_KEY)
    }

    override suspend fun getUserProfile(): UserProfile? {
        return localStorage.get<UserProfile>(LOCAL_USER_KEY)
    }
}