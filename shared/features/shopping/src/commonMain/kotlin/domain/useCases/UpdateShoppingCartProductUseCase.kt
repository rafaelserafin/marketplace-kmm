package domain.useCases

import Provider
import common.UseCase
import data.ShoppingRepository
import data.ShoppingRepositoryProvider
import data.session.ShoppingCartSession
import domain.entities.ShoppingCartProduct
import kotlinx.coroutines.flow.first

class UpdateShoppingCartProductUseCaseProvider : Provider<UpdateShoppingCartProductUseCase>() {
    override fun provide(): UpdateShoppingCartProductUseCase {
        return UpdateShoppingCartProductUseCase(
            ShoppingRepositoryProvider().provide()
        )
    }
}

class UpdateShoppingCartProductUseCase(
    private val repository: ShoppingRepository,
) : UseCase<ShoppingCartProduct, Unit>() {
    override suspend fun implementation(input: ShoppingCartProduct) {
        val products = if (input.quantity > 0) {
            ShoppingCartSession.add(input)
        } else {
            ShoppingCartSession.remove(input)
        }

        repository.saveShoppingCart(products)
    }
}