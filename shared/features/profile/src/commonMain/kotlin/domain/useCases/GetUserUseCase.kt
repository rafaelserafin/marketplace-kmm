package domain.useCases

import Provider
import common.UseCase
import common.errors.UnauthorizedException
import data.ProfileRepository
import data.ProfileRepositoryProvider
import domain.entities.UserProfile

class GetUserUseCaseProvider : Provider<GetUserUseCase>() {
    override fun provide(): GetUserUseCase {
        return GetUserUseCase(
            ProfileRepositoryProvider().provide()
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