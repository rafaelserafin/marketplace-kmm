package domain.useCases

import common.Resource
import kotlinx.coroutines.flow.first
import test.runBlockingTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetUserUseCaseTest {

    private val getUserUseCase: GetUserUseCase by GetUserUseCaseTestProvider()

    @BeforeTest
    fun setupStorage() {
        UseCaseTestFactories.fakeLocalStorage.clear()
    }

    @Test
    fun `when LocalStore is empty then getUserUseCase should return false`() = runBlockingTest {
        val result = getUserUseCase.execute(Unit).first()

        assertEquals(false, result.isSuccess)
    }

    @Test
    fun `when LocalStorage contains saved user then getUserUseCase should return UserProfile`() =
        runBlockingTest {
            UseCaseTestFactories.addFakeUser()

            val result = getUserUseCase.execute(Unit).first()

            assertEquals(true, result.isSuccess)
            assertEquals("test@test.com", (result as Resource.Success).data.email)
        }

}