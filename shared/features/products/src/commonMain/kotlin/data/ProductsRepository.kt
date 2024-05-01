package data

import domain.entities.ProductsCategory
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getUserHighlightedProducts() : Flow<ProductsCategory>

    suspend fun getMostPopularProducts() : Flow<ProductsCategory>

    suspend fun getSaleProducts() : Flow<ProductsCategory>

    suspend fun getTechProducts() : Flow<ProductsCategory>

    suspend fun getHealthProducts() : Flow<ProductsCategory>

}