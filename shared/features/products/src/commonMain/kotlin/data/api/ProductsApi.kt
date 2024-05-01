package data.api

import domain.entities.Product
import domain.entities.ProductsCategory
import kotlinx.coroutines.flow.Flow

interface ProductsApi {

    suspend fun getUserHighlightedProducts() : ProductsCategory

    suspend fun getSaleProducts() : ProductsCategory

    suspend fun getPopularProducts() : ProductsCategory

    suspend fun getTechProducts() : ProductsCategory

    suspend fun getHealthProducts() : ProductsCategory

    suspend fun searchProducts(query: String, offset: Int, limit: Int) : List<Product>
}