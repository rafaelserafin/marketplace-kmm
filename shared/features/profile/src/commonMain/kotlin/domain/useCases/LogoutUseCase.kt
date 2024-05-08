package domain.useCases

import Provider
import common.UseCase
import data.ProfileRepository
import data.ProfileRepositoryProvider
import data.session.UserSession

class LogoutUseCaseProvider : Provider<LogoutUseCase>() {
    override fun provide(): LogoutUseCase {
        return LogoutUseCase(
            ProfileRepositoryProvider().provide()
        )
    }
}

class LogoutUseCase(
    private val repository: ProfileRepository
) : UseCase<Unit, Unit>() {
    override suspend fun implementation(input: Unit) {
        UserSession.setUserProfile(null)
        repository.deleteUserSession()
    }
}