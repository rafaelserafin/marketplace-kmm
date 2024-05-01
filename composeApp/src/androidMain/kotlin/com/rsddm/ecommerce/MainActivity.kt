package com.rsddm.ecommerce

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import data.LocalStorage
import data.LocalStorageFactory
import kotlinx.coroutines.coroutineScope

class MainActivity : ComponentActivity() {

    val localStorage: LocalStorage by LocalStorageFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}