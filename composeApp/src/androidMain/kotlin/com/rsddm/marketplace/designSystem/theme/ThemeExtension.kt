package com.rsddm.marketplace.designSystem.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import domain.entities.Theme

fun Theme.toColorScheme() = lightColorScheme(
    primary = Color(primary),
    secondary = Color(secondary),
    tertiary = Color(tertiary),
    onPrimary = Color(onPrimary),
    onSecondary = Color(onSecondary),
    onTertiary = Color(onTertiary),
    background = Color(background),
    onBackground = Color(onBackground),
)