package data

import Provider
import data.api.ProductsApi
import data.api.ProductsApiImpl
import di.CoreContainer
import domain.entities.Product
import domain.entities.ProductDetail
import domain.entities.ProductsCategory
import domain.entities.Search
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepositoryProvider: Provider<ProductsRepository>() {
    override fun provide(): ProductsRepository =
        ProductsRepositoryImpl(
            ProductsApiImpl(CoreContainer.api)
        )
}

class ProductsRepositoryImpl(
    private val api: ProductsApi
) : ProductsRepository {
    override suspend fun getUserHighlightedProducts(): Flow<ProductsCategory> = flow {
        emit(api.getUserHighlightedProducts())
    }

    override suspend fun getMostPopularProducts(): Flow<ProductsCategory> = flow {
        emit(api.getPopularProducts())
    }

    override suspend fun getSaleProducts(): Flow<ProductsCategory> = flow {
        emit(api.getSaleProducts())
    }

    override suspend fun getTechProducts(): Flow<ProductsCategory> = flow {
        emit(api.getTechProducts())
    }

    override suspend fun getHealthProducts(): Flow<ProductsCategory> = flow {
        emit(api.getHealthProducts())
    }

    override suspend fun searchProducts(search: Search): List<Product> =
        api.searchProducts(search.query, search.offset, search.limit)

    override suspend fun getProductDetail(product: Product): ProductDetail =
        api.productDetail("any").copy(
            product = product
        )
}