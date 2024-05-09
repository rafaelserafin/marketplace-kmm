package domain.useCases

import Provider
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryProvider
import data.session.ShoppingCartSession
import domain.entities.ShoppingOrder

class FinalizePurchaseUseCaseProvider : Provider<FinalizePurchaseUseCase>() {
    override fun provide(): FinalizePurchaseUseCase {
        return FinalizePurchaseUseCase(
            ShoppingRepositoryProvider().provide()
        )
    }
}

class FinalizePurchaseUseCase(
    private val repository: ShoppingRepository
) : UseCase<ShoppingOrder, ShoppingOrder>() {
    override suspend fun implementation(input: ShoppingOrder): ShoppingOrder {
        return repository.finalizePurchase(input).apply {
            repository.savePurchaseLocal(input)

            ShoppingCartSession.removeAll()
            repository.saveShoppingCart(listOf())
        }
    }
}