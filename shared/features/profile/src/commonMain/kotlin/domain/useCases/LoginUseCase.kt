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

class LoginUseCaseFactory : Factory<LoginUseCase>() {
    override fun provide(): LoginUseCase {
        return LoginUseCase(
            ProfileRepositoryImpl(
                ProfileApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
        )
    }
}

class LoginUseCase(
    private val repository: ProfileRepository
) : UseCase<UserLogin, UserProfile>() {
    override suspend fun implementation(input: UserLogin): UserProfile {
        return repository.login(input).apply {
            UserSession.setUserProfile(this)

            repository.saveUserSession(this)
        }
    }
}