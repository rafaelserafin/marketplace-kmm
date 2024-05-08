package domain.useCases

import Provider
import bridge.ModuleBridge
import common.UseCase
import data.ProductRepositoryProvider
import data.ProductsRepository
import domain.entities.ProductsCategory
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class LoadHomeProductsUseCaseProvider : Provider<LoadHomeProductsUseCase>() {
    override fun provide(): LoadHomeProductsUseCase {
        return LoadHomeProductsUseCase(
            ProductRepositoryProvider().provide()
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