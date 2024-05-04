package com.rsddm.marketplace.features.profile.detail

import com.rsddm.marketplace.core.BaseActionBundle

class ProfileDetail {
    sealed class UIState {
        data object Default: UIState()
    }

    interface ActionBundle : BaseActionBundle {

    }
}
