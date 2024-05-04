package com.rsddm.marketplace.features.profile.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileDetailScreen(uiState: ProfileDetailUIState,
                        setupTopBar: (String) -> Unit) {

    val title = ""
    LaunchedEffect(true) {

    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Cyan)
    ) { }
}