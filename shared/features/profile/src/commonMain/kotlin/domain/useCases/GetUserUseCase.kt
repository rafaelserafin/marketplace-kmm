package domain.useCases

import Factory
import common.UseCase
import common.errors.UnauthorizedException
import data.ProfileRepository
import data.ProfileRepositoryImpl
import data.api.ProfileApiImpl
import data.session.UserSession
import di.CoreContainer
import domain.entities.UserLogin
import domain.entities.UserProfile

class GetUserUseCaseFactory : Factory<GetUserUseCase>() {
    override fun provide(): GetUserUseCase {
        return GetUserUseCase(
            ProfileRepositoryImpl(
                ProfileApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
        )
    }
}

class GetUserUseCase(
    private val repository: ProfileRepository
) : UseCase<Unit, UserProfile>() {
    override suspend fun implementation(input: Unit): UserProfile {
        return repository.getUserProfile() ?: throw UnauthorizedException()
    }
}