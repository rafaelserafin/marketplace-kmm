package domain.useCases

import Provider
import common.UseCase
import data.ProfileRepository
import data.ProfileRepositoryProvider
import data.session.UserSession
import domain.entities.UserLogin
import domain.entities.UserProfile

class LoginUseCaseProvider : Provider<LoginUseCase>() {
    override fun provide(): LoginUseCase {
        return LoginUseCase(
            ProfileRepositoryProvider().provide()
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