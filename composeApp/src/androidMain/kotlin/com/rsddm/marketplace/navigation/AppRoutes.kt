package com.rsddm.marketplace.navigation

sealed class AppRoutes(route: String) : Route(route) {
    data object PopBackStack: AppRoutes("pop")
    data object Profile: AppRoutes("profile")
    data object Products: AppRoutes("products")
    data object Shopping: AppRoutes("shopping")
}