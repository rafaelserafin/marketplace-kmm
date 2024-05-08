package domain.useCases

import bridge.ModuleBridge
import bridge.Profile
import common.Resource
import common.errors.UnauthorizedException
import data.api.errors.StockLackException
import data.api.errors.TransactionException
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import test.runBlockingTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FinalizePurchaseUseCaseTest {

    private val finalizePurchaseUseCase: FinalizePurchaseUseCase by FinalizePurchaseUseCaseTestProvider()

    @BeforeTest
    fun setupStorage() {
        UseCaseTestProvider.fakeLocalStorage.clear()
    }

    @Test
    fun `when user is not authenticated then return UnauthorizedException`() = runBlockingTest {
        ModuleBridge.buildBridge(object : Profile {
            override fun getUserSessionToken(): String? = null
            override fun isUserAuthenticated(): Boolean = false
        })

        val result = finalizePurchaseUseCase.execute(
            ShoppingOrder(
                products = listOf(),
                totalAmount = "",
                paymentDescription = "",
                deliveryForecast = "",
                createdAt = 0L
            )
        ).first()

        assertEquals(false, result.isSuccess)
        assertEquals(
            UnauthorizedException().message,
            (result as Resource.Error<*>).exception?.message
        )
    }

    @Test
    fun `when have no more items in stock then return StockLackException`() = runBlockingTest {
        ModuleBridge.buildBridge(object : Profile {
            override fun getUserSessionToken(): String = "123"
            override fun isUserAuthenticated(): Boolean = true
        })

        val result = finalizePurchaseUseCase.execute(
            ShoppingOrder(
                products = listOf(
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00",
                        quantity = 999
                    )
                ),
                totalAmount = "",
                paymentDescription = "",
                deliveryForecast = "",
                createdAt = 0L
            )
        ).first()

        assertEquals(false, result.isSuccess)
        assertEquals(
            StockLackException("item").message,
            (result as Resource.Error<*>).exception?.message
        )
    }

    @Test
    fun `when have no limit available then return TransactionException`() = runBlockingTest {
        ModuleBridge.buildBridge(object : Profile {
            override fun getUserSessionToken(): String = "123"
            override fun isUserAuthenticated(): Boolean = true
        })

        val result = finalizePurchaseUseCase.execute(
            ShoppingOrder(
                products = listOf(
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00",
                        quantity = 3
                    ),
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00",
                        quantity = 3
                    ),
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00",
                        quantity = 3
                    ),
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00",
                        quantity = 3
                    ),
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00",
                        quantity = 3
                    ),
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00",
                        quantity = 3
                    )
                ),
                totalAmount = "R$ 1000000,00",
                paymentDescription = "",
                deliveryForecast = "",
                createdAt = 0L
            )
        ).first()

        assertEquals(false, result.isSuccess)
        assertEquals(
            TransactionException().message,
            (result as Resource.Error<*>).exception?.message
        )
    }

    @Test
    fun `when shopping is valid then return isSuccess = true `() = runBlockingTest {
        ModuleBridge.buildBridge(object : Profile {
            override fun getUserSessionToken(): String = "123"
            override fun isUserAuthenticated(): Boolean = true
        })

        val result = finalizePurchaseUseCase.execute(
            ShoppingOrder(
                products = listOf(
                    ShoppingCartProduct(
                        name = "item",
                        price = "R$ 10,00"
                    )
                ),
                totalAmount = "",
                paymentDescription = "",
                deliveryForecast = "",
                createdAt = 0L
            )
        ).first()

        assertEquals(true, result.isSuccess)
        assertEquals(
            1,
            UseCaseTestProvider.fakeLocalStorage.count(),
        )
    }

}