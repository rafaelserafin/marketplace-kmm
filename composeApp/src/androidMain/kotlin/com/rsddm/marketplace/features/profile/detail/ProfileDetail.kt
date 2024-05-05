package com.rsddm.marketplace.features.profile.detail

import com.rsddm.marketplace.core.BaseActionBundle
import domain.entities.ShoppingOrder
import domain.entities.UserProfile

class ProfileDetail {
    sealed class UIState {
        data object Loading: UIState()
        data class Detail(val userProfile: UserProfile, val orders: List<ShoppingOrder>): UIState()
    }

    interface ActionBundle : BaseActionBundle {
        fun logout()
    }
}
