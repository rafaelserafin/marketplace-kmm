package session

import data.LocalStorage
import data.LocalStorageFactory

private const val AUTHENTICATION_KEY = "authentication"

object Session {

    private val localStorage: LocalStorage by LocalStorageFactory()

    suspend fun isAuthenticated() = !localStorage.get(AUTHENTICATION_KEY).isNullOrBlank()

    suspend fun signIn(authentication: String) {
        localStorage.save(AUTHENTICATION_KEY, authentication)
    }

    suspend fun signOut() {
        localStorage.save(AUTHENTICATION_KEY, "")
    }

}