package com.rsddm.marketplace.core

import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.rsddm.marketplace.designSystem.theme.Blue
import com.rsddm.marketplace.designSystem.theme.MarketplaceTheme
import com.rsddm.marketplace.designSystem.theme.Orange
import com.rsddm.marketplace.designSystem.theme.OrangeVariant
import org.junit.Rule

abstract class BaseSnapshot {

    @get: Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    fun colorScheme() = lightColorScheme(
        primary = Orange,
        secondary = OrangeVariant,
        tertiary = Blue,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onTertiary = Color.White,
        background = Color.White,
        onBackground = Color.Black,
    )

    @Composable
    fun Theme(content: @Composable () -> Unit) {
        MarketplaceTheme(
            colorScheme = colorScheme()
        ) {
            Surface {
                content()
            }
        }
    }
}