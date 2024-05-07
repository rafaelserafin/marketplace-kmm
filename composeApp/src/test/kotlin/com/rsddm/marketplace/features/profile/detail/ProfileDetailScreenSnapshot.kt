package com.rsddm.marketplace.features.profile.detail

import com.rsddm.marketplace.core.BaseSnapshot
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import domain.entities.UserProfile
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ProfileDetailScreenSnapshot(private val uiState: ProfileDetail.UIState) : BaseSnapshot() {

    @Test
    fun snapshot() {
        paparazzi.snapshot {
            Theme {
                ProfileDetailScreen(uiState,
                    object : ProfileDetail.ActionBundle {
                        override fun logout() {}
                        override fun setupTopBar(title: String) {}
                    })
            }
        }
    }

    companion object {
        private val userProfile = UserProfile(
            name = "Test",
            birthday = "06/05/2024",
            email = "test@test.com",
            token = "token"
        )

        private val shoppingOrder1 = ShoppingOrder(
            products = listOf(
                ShoppingCartProduct("", "")
            ),
            totalAmount = "R$ 100,00",
            paymentDescription = "",
            deliveryForecast = "",
            createdAt = 18453213321L
        )

        private val shoppingOrder2 = ShoppingOrder(
            products = listOf(
                ShoppingCartProduct("", ""),
                ShoppingCartProduct("", ""),
                ShoppingCartProduct("", ""),
            ),
            totalAmount = "R$ 999,00",
            paymentDescription = "",
            deliveryForecast = "",
            createdAt = 18453213321L
        )


        @JvmStatic
        @Parameterized.Parameters
        fun params() = listOf(
            ProfileDetail.UIState.Loading,
            ProfileDetail.UIState.Detail(userProfile, listOf()),
            ProfileDetail.UIState.Detail(
                userProfile, listOf(
                    shoppingOrder1,
                    shoppingOrder2
                )
            )
        )
    }
}