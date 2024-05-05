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

class LogoutUseCaseFactory : Factory<LogoutUseCase>() {
    override fun provide(): LogoutUseCase {
        return LogoutUseCase(
            ProfileRepositoryImpl(
                ProfileApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
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