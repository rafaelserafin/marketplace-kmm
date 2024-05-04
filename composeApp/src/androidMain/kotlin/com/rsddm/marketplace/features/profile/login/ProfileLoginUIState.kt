package com.rsddm.marketplace.features.profile.login

sealed class ProfileLoginUIState() {
    data object Default: ProfileLoginUIState()
}