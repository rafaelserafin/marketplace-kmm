package domain.useCases

import Factory
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryImpl
import data.api.ShoppingApiImpl
import data.session.ShoppingCartSession
import di.CoreContainer
import domain.entities.ShoppingOrder

class FinalizePurchaseUseCaseFactory : Factory<FinalizePurchaseUseCase>() {
    override fun provide(): FinalizePurchaseUseCase {
        return FinalizePurchaseUseCase(
            ShoppingRepositoryImpl(
                ShoppingApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
        )
    }
}

class FinalizePurchaseUseCase(
    private val repository: ShoppingRepository
) : UseCase<ShoppingOrder, ShoppingOrder>() {
    override suspend fun implementation(input: ShoppingOrder): ShoppingOrder {
        return repository.finalizePurchase(input).apply {
            ShoppingCartSession.removeAll()
        }
    }
}