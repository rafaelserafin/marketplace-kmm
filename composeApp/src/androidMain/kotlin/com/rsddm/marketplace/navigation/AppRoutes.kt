package com.rsddm.marketplace.navigation

sealed class AppRoutes(route: String) : Route(route) {
    data object Setup: AppRoutes("setup")
    data object Products: AppRoutes("products")
    data object ShoppingCart: AppRoutes("shopping_cart")
}