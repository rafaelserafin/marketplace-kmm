package com.rsddm.marketplace.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.rsddm.marketplace.designSystem.theme.Orange

@Composable
fun SplashScreen() {

//    CompositionLocalProvider()


    Box(modifier = Modifier.fillMaxSize()
        .background(Orange),
        contentAlignment = Alignment.Center
    ) {

        Text("MarktplaceApp")

    }

}