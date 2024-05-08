package domain.useCases

import Provider
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryProvider
import domain.entities.ShoppingOrder

class GetShoppingOrdersUseCaseProvider : Provider<GetShoppingOrdersUseCase>() {
    override fun provide(): GetShoppingOrdersUseCase {
        return GetShoppingOrdersUseCase(
            ShoppingRepositoryProvider().provide()
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