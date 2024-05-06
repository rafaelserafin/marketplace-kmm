package domain.useCases

import common.Resource
import domain.entities.UserLogin
import kotlinx.coroutines.flow.first
import test.runBlockingTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LoginUseCaseTest {

    private val loginUseCase: LoginUseCase by LoginUseCaseTestFactory()

    @BeforeTest
    fun setupStorage() {
        UseCaseTestFactories.fakeLocalStorage.clear()
    }

    @Test
    fun `when username is wrong then loginUseCase should return false`() = runBlockingTest {
        val result = loginUseCase.execute(
            UserLogin(
                "wrong", "123456"
            )
        ).first()

        assertEquals(false, result.isSuccess)
    }

    @Test
    fun `when password is wrong then loginUseCase should return false`() = runBlockingTest {
        val result = loginUseCase.execute(
            UserLogin(
                "test@test.com", "wrong"
            )
        ).first()

        assertEquals(false, result.isSuccess)
    }

    @Test
    fun `when username and password match then loginUseCase should return UserProfile`() =
        runBlockingTest {
            val result = loginUseCase.execute(
                UserLogin(
                    "test@test.com", "123456"
                )
            ).first()

            assertEquals(true, result.isSuccess)
            assertEquals("test@test.com", (result as Resource.Success).data.email)
        }
}