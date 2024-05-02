package data

import domain.entities.Product
import domain.entities.ProductDetail
import domain.entities.ProductsCategory
import domain.entities.Search
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getUserHighlightedProducts() : Flow<ProductsCategory>

    suspend fun getMostPopularProducts() : Flow<ProductsCategory>

    suspend fun getSaleProducts() : Flow<ProductsCategory>

    suspend fun getTechProducts() : Flow<ProductsCategory>

    suspend fun getHealthProducts() : Flow<ProductsCategory>

    suspend fun searchProducts(search: Search) : List<Product>

    suspend fun getProductDetail(product: Product) : ProductDetail

}