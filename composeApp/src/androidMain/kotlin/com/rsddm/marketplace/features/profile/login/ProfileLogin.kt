package com.rsddm.marketplace.features.profile.login

import com.rsddm.marketplace.core.BaseActionBundle

class ProfileLogin {
    sealed class UIState {
        data object Default : UIState()
    }

    interface ActionBundle : BaseActionBundle {
        fun login(username: String, password: String)
    }
}


