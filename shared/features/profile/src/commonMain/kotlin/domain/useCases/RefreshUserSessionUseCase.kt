package domain.useCases

import Factory
import common.UseCase
import data.ProfileRepository
import data.ProfileRepositoryImpl
import data.api.ProfileApiImpl
import data.session.UserSession
import di.CoreContainer
import domain.entities.UserLogin
import domain.entities.UserProfile

class RefreshUserSessionUseCaseFactory : Factory<RefreshUserSessionUseCase>() {
    override fun provide(): RefreshUserSessionUseCase {
        return RefreshUserSessionUseCase(
            ProfileRepositoryImpl(
                ProfileApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
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