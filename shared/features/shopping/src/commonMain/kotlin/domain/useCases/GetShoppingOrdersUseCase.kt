package domain.useCases

import Factory
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryImpl
import data.api.ShoppingApiImpl
import di.CoreContainer
import domain.entities.ShoppingOrder

class GetShoppingOrdersUseCaseFactory : Factory<GetShoppingOrdersUseCase>() {
    override fun provide(): GetShoppingOrdersUseCase {
        return GetShoppingOrdersUseCase(
            ShoppingRepositoryImpl(
                ShoppingApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
        )
    }
}

class GetShoppingOrdersUseCase(
    private val repository: ShoppingRepository
) : UseCase<Unit, List<ShoppingOrder>>() {
    override suspend fun implementation(input: Unit): List<ShoppingOrder> {
        return repository.getOrders()
    }
}