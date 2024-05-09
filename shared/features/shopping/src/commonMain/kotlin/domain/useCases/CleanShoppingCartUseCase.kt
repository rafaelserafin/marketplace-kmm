package domain.useCases

import Provider
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryProvider
import data.session.ShoppingCartSession
import domain.entities.ShoppingCartProduct

class CleanSHoppingCartUseCaseProvider : Provider<CleanSHoppingCartUseCase>() {
    override fun provide(): CleanSHoppingCartUseCase {
        return CleanSHoppingCartUseCase()
    }
}

class CleanSHoppingCartUseCase : UseCase<Unit, Unit>() {
    override suspend fun implementation(input: Unit) {
        ShoppingCartSession.removeAll()
    }
}