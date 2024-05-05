package domain.useCases

import Factory
import bridge.ModuleBridge
import common.UseCase
import data.ProductsRepository
import data.ProductsRepositoryImpl
import data.api.ProductsApiImpl
import di.CoreContainer
import domain.entities.ProductsCategory
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class LoadHomeProductsUseCaseFactory : Factory<LoadHomeProductsUseCase>() {
    override fun provide(): LoadHomeProductsUseCase {
        return LoadHomeProductsUseCase(
            ProductsRepositoryImpl(
                ProductsApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
        )
    }
}

class LoadHomeProductsUseCase(
    private val repository: ProductsRepository
) : UseCase<Unit, List<ProductsCategory>>() {
    override suspend fun implementation(input: Unit): List<ProductsCategory> {
        val firstCategoryFlow =
            if (ModuleBridge.profile?.getUserSessionToken() != null) repository.getUserHighlightedProducts() else repository.getMostPopularProducts()

        return combine(
            firstCategoryFlow,
            repository.getSaleProducts(),
            repository.getTechProducts(),
            repository.getHealthProducts()
        ) { highlighted, sale, tech, health ->
            listOf(
                highlighted,
                sale,
                tech,
                health
            )
        }.first()
    }
}