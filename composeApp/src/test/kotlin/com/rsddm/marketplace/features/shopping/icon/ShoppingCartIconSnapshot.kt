package com.rsddm.marketplace.features.shopping.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import com.rsddm.marketplace.core.BaseSnapshot
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ShoppingCartIconSnapshot(private val uiState: ShoppingCartIcon.UIState) :
    BaseSnapshot() {

    @Test
    fun snapshot() {
        paparazzi.snapshot {
            Theme {
                Box(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                    ShoppingCartIcon(uiState,
                        object : ShoppingCartIcon.ActionBundle {
                            override fun onShoppingCartClick() {}
                            override fun setupTopBar(title: String) {}
                        })
                }
            }
        }
    }

    companion object {
        @JvmStatic
        @Parameters
        fun params() = listOf(
            ShoppingCartIcon.UIState.Badge(0),
            ShoppingCartIcon.UIState.Badge(1),
            ShoppingCartIcon.UIState.Badge(2),
            ShoppingCartIcon.UIState.Badge(99),
        )
    }
}