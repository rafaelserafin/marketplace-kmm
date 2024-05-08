package data

import Provider
import data.api.ProfileApi
import data.api.ProfileApiImpl
import di.CoreContainer
import domain.entities.UserLogin
import domain.entities.UserProfile

private const val LOCAL_USER_KEY = "user_session"

class ProfileRepositoryProvider : Provider<ProfileRepository>() {
    override fun provide(): ProfileRepository =
        ProfileRepositoryImpl(
            ProfileApiImpl(CoreContainer.api),
            CoreContainer.localStorage
        )
}

class ProfileRepositoryImpl(
    private val api: ProfileApi,
    private val localStorage: LocalStorage,
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