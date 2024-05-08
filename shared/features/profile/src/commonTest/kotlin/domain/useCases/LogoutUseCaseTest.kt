package domain.useCases

import kotlinx.coroutines.flow.first
import test.runBlockingTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LogoutUseCaseTest {

    private val logoutUseCase: LogoutUseCase by LogoutUseCaseTestProvider()

    @BeforeTest
    fun setupStorage() {
        UseCaseTestProvider.fakeLocalStorage.clear()
    }

    @Test
    fun `when logout then logoutUseCase should delete saved user`() = runBlockingTest {
        UseCaseTestProvider.addFakeUser()

        logoutUseCase.execute(Unit).first()

        assertEquals(null, UseCaseTestProvider.fakeLocalStorage.getString("user_session"))
    }
}