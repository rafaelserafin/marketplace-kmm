package domain.useCases

import Provider
import data.ShoppingRepositoryImpl
import domain.data.FakeLocalStorage

object UseCaseTestProvider {
    internal val fakeLocalStorage = FakeLocalStorage()

    val repository = ShoppingRepositoryImpl(
        fakeLocalStorage
    )

    suspend fun createFakeSession() {
        fakeLocalStorage
            .saveString(
                "user_session",
                "{\"name\":\"Rogerio da Silva\",\"email\":\"test@test.com\",\"birthday\":\"04/05/2024\",\"token\":\"fakeauthenticationtoken_1\"}"
            )
    }
}

class FinalizePurchaseUseCaseTestProvider : Provider<FinalizePurchaseUseCase>() {
    override fun provide(): FinalizePurchaseUseCase =
        FinalizePurchaseUseCase(
            repository = UseCaseTestProvider.repository
        )
}