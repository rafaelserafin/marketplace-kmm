package domain.useCases

import Factory
import common.UseCase
import data.ProductsRepository
import data.ProductsRepositoryImpl
import data.api.ProductsApiImpl
import di.CoreContainer
import domain.entities.Product
import domain.entities.Search

class SearchProductsUseCaseFactory : Factory<SearchProductsUseCase>() {
    override fun provide(): SearchProductsUseCase {
        return SearchProductsUseCase(
            ProductsRepositoryImpl(
                ProductsApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
        )
    }
}

class SearchProductsUseCase(private val repository: ProductsRepository) : UseCase<Search, List<Product>>() {
    override suspend fun implementation(input: Search): List<Product> {
        return repository.searchProducts(input)
    }
}