package domain.useCases

import Provider
import common.UseCase
import data.ProfileRepository
import data.ProfileRepositoryProvider
import data.session.UserSession

class RefreshUserSessionUseCaseProvider : Provider<RefreshUserSessionUseCase>() {
    override fun provide(): RefreshUserSessionUseCase {
        return RefreshUserSessionUseCase(
            ProfileRepositoryProvider().provide()
        )
    }
}

class RefreshUserSessionUseCase(
    private val repository: ProfileRepository
) : UseCase<Unit, Unit?>() {
    override suspend fun implementation(input: Unit) {
        repository.getUserProfile()?.let {
            UserSession.setUserProfile(it)
        }
    }
}