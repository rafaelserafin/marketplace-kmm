package data.api

import domain.entities.Product
import domain.entities.ProductsCategory
import kotlinx.coroutines.delay
import network.Api

class ProductsApiImpl(private val api: Api) : ProductsApi {
    override suspend fun getUserHighlightedProducts(): ProductsCategory {
        delay(250)

        return api.get<ProductsCategory>("products/userHighlighted")
    }

    override suspend fun getSaleProducts(): ProductsCategory {
        delay(50)

        return api.get<ProductsCategory>("products/sale")
    }

    override suspend fun getPopularProducts(): ProductsCategory {
        delay(350)

        return api.get<ProductsCategory>("products/popular")
    }

    override suspend fun getTechProducts(): ProductsCategory {
        delay(150)

        return api.get<ProductsCategory>("products/tech")
    }

    override suspend fun getHealthProducts(): ProductsCategory {
        delay(100)

        return api.get<ProductsCategory>("products/health")
    }

    override suspend fun searchProducts(query: String, offset: Int, limit: Int): List<Product> {
        delay(1000)

        //Ignore query for fake backend
        return api.get<List<Product>>("products?query=any&offset=$offset&limit=$limit")
    }
}