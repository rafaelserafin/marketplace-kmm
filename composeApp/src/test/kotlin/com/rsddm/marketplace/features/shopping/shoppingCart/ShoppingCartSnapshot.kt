package com.rsddm.marketplace.features.shopping.shoppingCart

import com.rsddm.marketplace.core.BaseSnapshot
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters

@RunWith(Parameterized::class)
class ShoppingCartSnapshot(private val uiState: ShoppingCart.UIState) :
    BaseSnapshot() {

    @Test
    fun snapshot() {
        paparazzi.snapshot {
            Theme {
                ShoppingCartScreen(uiState,
                    object : ShoppingCart.ActionBundle {
                        override fun updateProductQuantity(
                            product: ShoppingCartProduct,
                            quantity: Int
                        ) {
                        }

                        override fun onBuyClick() {}
                        override fun goToMyOrders() {}
                        override fun setupTopBar(title: String) {}
                    })
            }
        }
    }

    companion object {

        private val products = listOf(
            ShoppingCartProduct("test 1", "R$ 50,00"),
            ShoppingCartProduct("test 2", "R$ 50,00", 2),
            ShoppingCartProduct("test 3", "R$ 50,00", 3),
        )

        @JvmStatic
        @Parameters
        fun params() = listOf(
            ShoppingCart.UIState.Empty,
            ShoppingCart.UIState.CheckIn(products, "R$ 1000,00"),
            ShoppingCart.UIState.FinalizingPurchase,
            ShoppingCart.UIState.OrderError("error", "action") { },
            ShoppingCart.UIState.OrderSuccess(
                ShoppingOrder(
                    products,
                    "",
                    "",
                    "",
                    0L
                )
            ),
        )
    }
}