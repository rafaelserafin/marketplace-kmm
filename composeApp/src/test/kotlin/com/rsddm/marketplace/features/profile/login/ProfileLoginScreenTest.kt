package com.rsddm.marketplace.features.profile.login

import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.rsddm.marketplace.designSystem.theme.Blue
import com.rsddm.marketplace.designSystem.theme.MarketplaceTheme
import com.rsddm.marketplace.designSystem.theme.Orange
import com.rsddm.marketplace.designSystem.theme.OrangeVariant
import domain.entities.UserLogin
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ProfileLoginScreenTest(private val uiState: ProfileLogin.UIState) {

    @get: Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun snapshot() {
        paparazzi.snapshot {
            MarketplaceTheme(colorScheme = lightColorScheme(
                primary = Orange,
                secondary = OrangeVariant,
                tertiary = Blue,
                onPrimary = Color.White,
                onSecondary = Color.White,
                onTertiary = Color.White,
                background = Color.White,
                onBackground = Color.Black,
            )
            ) {
                Surface {
                    ProfileLoginScreen(uiState = uiState,
                        actionBundle = object : ProfileLogin.ActionBundle {
                            override fun login(username: String, password: String) {}
                            override fun setupTopBar(title: String) {}
                        })
                }
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