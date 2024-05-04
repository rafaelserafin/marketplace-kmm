package domain.useCases

import Factory
import common.UseCase
import data.session.ShoppingCartSession
import domain.entities.ShoppingCartProduct

class UpdateShoppingCartProductUseCaseFactory : Factory<UpdateShoppingCartProductUseCase>() {
    override fun provide(): UpdateShoppingCartProductUseCase {
        return UpdateShoppingCartProductUseCase()
    }
}

class UpdateShoppingCartProductUseCase : UseCase<ShoppingCartProduct, Unit>() {
    override suspend fun implementation(input: ShoppingCartProduct) {
        if (input.quantity > 0) {
            ShoppingCartSession.add(input)
        } else {
            ShoppingCartSession.remove(input)
        }
    }
}