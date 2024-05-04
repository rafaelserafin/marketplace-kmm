package domain.useCases

import Factory
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryImpl
import data.api.ShoppingApiImpl
import data.session.ShoppingCartSession
import di.CoreContainer
import domain.entities.ShoppingCartProduct
import domain.entities.ShoppingOrder
import session.Session

class LoadShoppingCartUseCaseFactory : Factory<LoadShoppingCartUseCase>() {
    override fun provide(): LoadShoppingCartUseCase {
        return LoadShoppingCartUseCase(
            ShoppingRepositoryImpl(
                ShoppingApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
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