package domain.useCases

import Factory
import common.UseCase
import data.LocalStorage
import data.ProductsRepository
import data.ProductsRepositoryImpl
import data.api.ProductsApiImpl
import data.createDataStore
import di.CoreContainer
import domain.entities.ProductsCategory
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import network.Api
import session.Session

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
            if (Session.isAuthenticated()) repository.getUserHighlightedProducts() else repository.getMostPopularProducts()

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