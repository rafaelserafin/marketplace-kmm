package domain.useCases

import Provider
import common.UseCase
import data.ProductRepositoryProvider
import data.ProductsRepository
import domain.entities.Product
import domain.entities.Search

class SearchProductsUseCaseProvider : Provider<SearchProductsUseCase>() {
    override fun provide(): SearchProductsUseCase {
        return SearchProductsUseCase(
            ProductRepositoryProvider().provide()
        )
    }
}

class SearchProductsUseCase(private val repository: ProductsRepository) : UseCase<Search, List<Product>>() {
    override suspend fun implementation(input: Search): List<Product> {
        return repository.searchProducts(input)
    }
}