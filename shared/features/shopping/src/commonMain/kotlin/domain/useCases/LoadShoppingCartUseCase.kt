package domain.useCases

import Provider
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryProvider
import data.session.ShoppingCartSession
import domain.entities.ShoppingCartProduct

class LoadShoppingCartUseCaseProvider : Provider<LoadShoppingCartUseCase>() {
    override fun provide(): LoadShoppingCartUseCase {
        return LoadShoppingCartUseCase(
            ShoppingRepositoryProvider().provide()
        )
    }
}

class LoadShoppingCartUseCase(
    private val repository: ShoppingRepository
) : UseCase<Unit, List<ShoppingCartProduct>>() {
    override suspend fun implementation(input: Unit): List<ShoppingCartProduct> {
        return repository.getShoppingCart().apply {
            ShoppingCartSession.addAll(this)
        }
    }
}