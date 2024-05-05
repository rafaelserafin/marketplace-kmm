package data.session

import domain.entities.UserProfile

private const val AUTHENTICATION_KEY = "authentication"

object UserSession {

    var user: UserProfile? = null
        private set

    fun isAuthenticated() = user != null

    internal fun setUserProfile(userProfile: UserProfile?) {
        user = userProfile
    }
}
