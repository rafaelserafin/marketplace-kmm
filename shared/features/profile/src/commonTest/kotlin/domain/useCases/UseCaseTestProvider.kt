package domain.useCases

import Provider
import data.ProfileRepositoryImpl
import data.api.ProfileApiImpl
import domain.data.FakeLocalStorage
import network.Api

object UseCaseTestProvider {
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

class GetUserUseCaseTestProvider : Provider<GetUserUseCase>() {
    override fun provide(): GetUserUseCase =
        GetUserUseCase(UseCaseTestProvider.repository)
}

class LoginUseCaseTestProvider : Provider<LoginUseCase>() {
    override fun provide(): LoginUseCase =
        LoginUseCase(UseCaseTestProvider.repository)
}

class LogoutUseCaseTestProvider : Provider<LogoutUseCase>() {
    override fun provide(): LogoutUseCase =
        LogoutUseCase(UseCaseTestProvider.repository)
}

class RefreshUserSessionUseCaseTestProvider : Provider<RefreshUserSessionUseCase>() {
    override fun provide(): RefreshUserSessionUseCase =
        RefreshUserSessionUseCase(UseCaseTestProvider.repository)
}