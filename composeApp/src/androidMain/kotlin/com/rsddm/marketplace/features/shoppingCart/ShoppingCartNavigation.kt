package com.rsddm.marketplace.features.shoppingCart

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rsddm.marketplace.features.shoppingCart.shoppingCart.ShoppingCartScreen
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.Route

fun NavGraphBuilder.shoppingCartNavigation(navigator: Navigator) {
    navigation(
        route = AppRoutes.ShoppingCart.route,
        startDestination = ShoppingCartRoutes.ShoppingCartProducts.route
    ) {
        composable(route = ShoppingCartRoutes.ShoppingCartProducts.route) {
            ShoppingCartScreen()
        }
    }
}

sealed class ShoppingCartRoutes(route: String) : Route(route) {
    data object ShoppingCartProducts : ShoppingCartRoutes("shopping_cart_products")
}