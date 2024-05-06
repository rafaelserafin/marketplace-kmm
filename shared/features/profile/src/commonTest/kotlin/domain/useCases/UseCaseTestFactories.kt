package domain.useCases

import Factory
import data.ProfileRepositoryImpl
import data.api.ProfileApiImpl
import network.Api

object UseCaseTestFactories {
    val fakeLocalStorage = FakeLocalStorage()

    val repository = ProfileRepositoryImpl(
        ProfileApiImpl(api = Api()),
        fakeLocalStorage
    )

    suspend fun addFakeUser() {
        fakeLocalStorage
            .saveString(
                "user_session",
                "{\"name\":\"Rogerio da Silva\",\"email\":\"test@test.com\",\"birthday\":\"04/05/2024\",\"token\":\"fakeauthenticationtoken_1\"}"
            )
    }
}

class GetUserUseCaseTestFactory : Factory<GetUserUseCase>() {
    override fun provide(): GetUserUseCase =
        GetUserUseCase(UseCaseTestFactories.repository)
}

class LoginUseCaseTestFactory : Factory<LoginUseCase>() {
    override fun provide(): LoginUseCase =
        LoginUseCase(UseCaseTestFactories.repository)
}

class LogoutUseCaseTestFactory : Factory<LogoutUseCase>() {
    override fun provide(): LogoutUseCase =
        LogoutUseCase(UseCaseTestFactories.repository)
}

class RefreshUserSessionUseCaseTestFactory : Factory<RefreshUserSessionUseCase>() {
    override fun provide(): RefreshUserSessionUseCase =
        RefreshUserSessionUseCase(UseCaseTestFactories.repository)
}