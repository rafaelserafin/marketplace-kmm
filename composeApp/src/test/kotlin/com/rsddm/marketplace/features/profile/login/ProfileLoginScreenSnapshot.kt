package com.rsddm.marketplace.features.profile.login

import com.rsddm.marketplace.core.BaseSnapshot
import domain.entities.UserLogin
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ProfileLoginScreenSnapshot(private val uiState: ProfileLogin.UIState) :
    BaseSnapshot() {

    @Test
    fun snapshot() {
        paparazzi.snapshot {
            Theme {
                ProfileLoginScreen(uiState = uiState,
                    actionBundle = object : ProfileLogin.ActionBundle {
                        override fun login(username: String, password: String) {}
                        override fun setupTopBar(title: String) {}
                    })
            }
        }
    }

    companion object {
        @JvmStatic
        @Parameters
        fun params(): List<ProfileLogin.UIState> =
            listOf(
                ProfileLogin.UIState.Idle,
                ProfileLogin.UIState.Loading,
                ProfileLogin.UIState.Error(UserLogin("test@test.com", "123456"), "error message")
            )
    }

}