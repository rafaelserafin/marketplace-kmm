package domain.useCases

import data.session.UserSession
import kotlinx.coroutines.flow.first
import test.runBlockingTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshUserSessionUseCaseTest {

    private val refreshUserSessionUseCase: RefreshUserSessionUseCase by RefreshUserSessionUseCaseTestProvider()

    @BeforeTest
    fun setupStorage() {
        UseCaseTestProvider.fakeLocalStorage.clear()
    }

    @Test
    fun `when not exists saved user then refreshUserSessionUseCase do nothing`() = runBlockingTest {
        refreshUserSessionUseCase.execute(Unit).first()

        assertEquals(null, UserSession.user)
    }

    @Test
    fun `when exists saved user then refreshUserSessionUseCase should add it to UserSession`() = runBlockingTest {
        UseCaseTestProvider.addFakeUser()

        refreshUserSessionUseCase.execute(Unit).first()

        assertEquals("test@test.com", UserSession.user?.email)
    }
}