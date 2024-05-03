package com.rsddm.marketplace.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.rsddm.marketplace.R
import com.rsddm.marketplace.designSystem.theme.Orange

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Orange),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
    }
}