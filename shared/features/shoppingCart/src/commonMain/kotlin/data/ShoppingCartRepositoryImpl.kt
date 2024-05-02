package data

import data.api.ShoppingCartApi

class ShoppingCartRepositoryImpl(
    private val api: ShoppingCartApi,
    private val localStorage: LocalStorage
) : ShoppingCartRepository {

}