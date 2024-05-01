package data

import data.api.ProductsApi
import domain.entities.Product
import domain.entities.ProductsCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductsRepositoryImpl(
    private val api: ProductsApi,
    private val localStorage: LocalStorage
) : ProductsRepository {
    override suspend fun getUserHighlightedProducts(): Flow<ProductsCategory> = flow {
        api.getUserHighlightedProducts()
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
}