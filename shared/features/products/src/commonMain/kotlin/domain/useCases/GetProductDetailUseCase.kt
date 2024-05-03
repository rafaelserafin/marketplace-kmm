package domain.useCases

import Factory
import common.UseCase
import data.ProductsRepository
import data.ProductsRepositoryImpl
import data.api.ProductsApiImpl
import di.CoreContainer
import domain.entities.Product
import domain.entities.ProductDetail

class GetProductDetailUseCaseFactory : Factory<GetProductDetailUseCase>() {
    override fun provide(): GetProductDetailUseCase {
        return GetProductDetailUseCase(
            ProductsRepositoryImpl(
                ProductsApiImpl(CoreContainer.api),
                CoreContainer.localStorage
            )
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