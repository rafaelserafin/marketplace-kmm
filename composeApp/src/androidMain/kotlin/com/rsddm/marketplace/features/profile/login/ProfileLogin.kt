package com.rsddm.marketplace.features.profile.login

import com.rsddm.marketplace.core.BaseActionBundle
import domain.entities.UserLogin

class ProfileLogin {
    sealed class UIState {
        data object Idle : UIState()
        data object Loading : UIState()
        data class Error(val userLogin: UserLogin, val message: String?) : UIState()
    }

    interface ActionBundle : BaseActionBundle {
        fun login(username: String, password: String)
    }
}


