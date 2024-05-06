package com.rsddm.marketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsddm.marketplace.designSystem.theme.MarketplaceTheme
import com.rsddm.marketplace.designSystem.theme.toColorScheme
import com.rsddm.marketplace.splash.SetupState
import com.rsddm.marketplace.splash.SetupViewModel
import com.rsddm.marketplace.splash.SplashScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val setupViewModel: SetupViewModel = viewModel()
            val state = setupViewModel.state.collectAsStateWithLifecycle()

            when (state.value) {
                SetupState.Splash -> SplashScreen()

                is SetupState.Finish -> {
                    val theme = (state.value as SetupState.Finish).theme
                    MarketplaceTheme(theme.toColorScheme()) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            App()
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}