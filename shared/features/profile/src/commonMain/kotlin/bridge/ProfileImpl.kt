package bridge

import data.session.UserSession

class ProfileImpl : Profile {
    override fun getUserSessionToken(): String? {
        return UserSession.user?.token
    }

    override fun isUserAuthenticated(): Boolean {
        return UserSession.user != null
    }
}