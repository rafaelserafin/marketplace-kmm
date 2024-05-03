package session

import di.CoreContainer

private const val AUTHENTICATION_KEY = "authentication"

object Session {
    suspend fun isAuthenticated() = !CoreContainer.localStorage.getString(AUTHENTICATION_KEY).isNullOrBlank()

    suspend fun signIn(authentication: String) {
        CoreContainer.localStorage.save(AUTHENTICATION_KEY, authentication)
    }

    suspend fun signOut() {
        CoreContainer.localStorage.save(AUTHENTICATION_KEY, "")
    }
}