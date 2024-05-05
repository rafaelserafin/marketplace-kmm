package data.api

import domain.entities.UserProfile
import kotlinx.coroutines.delay
import network.Api

class ProfileApiImpl(private val api: Api) : ProfileApi {
    override suspend fun login(username: String, password: String): UserProfile {
        delay(1200)

        return api.get<UserProfile>("user/username=${username}&password=${password}")
    }
}