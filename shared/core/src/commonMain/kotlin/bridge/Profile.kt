package bridge

interface Profile {

    fun getUserSessionToken(): String?
    fun isUserAuthenticated(): Boolean

}