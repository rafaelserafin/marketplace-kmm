package bridge

object ModuleBridge {
    var profile: Profile? = null
        private set

    fun buildBridge(
        profile: Profile
    ) {
        this.profile = profile
    }

}