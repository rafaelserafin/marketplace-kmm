package domain.useCases

import Provider
import common.UseCase
import data.ProductRepositoryProvider
import data.ProductsRepository
import domain.entities.Product
import domain.entities.ProductDetail

class GetProductDetailUseCaseProvider : Provider<GetProductDetailUseCase>() {
    override fun provide(): GetProductDetailUseCase {
        return GetProductDetailUseCase(
            ProductRepositoryProvider().provide()
        )
    }
}

class GetProductDetailUseCase(
    private val repository: ProductsRepository
) : UseCase<Product, ProductDetail>() {
    override suspend fun implementation(input: Product): ProductDetail {
        return repository.getProductDetail(input)
    }
}