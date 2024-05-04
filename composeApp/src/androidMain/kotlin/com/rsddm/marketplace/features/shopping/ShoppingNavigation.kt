package com.rsddm.marketplace.features.shopping

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rsddm.marketplace.features.shopping.shoppingCart.ShoppingCartScreen
import com.rsddm.marketplace.features.shopping.shoppingCart.ShoppingCartViewModel
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.Route

fun NavGraphBuilder.shoppingNavigation(navigator: Navigator) {
    navigation(
        route = AppRoutes.Shopping.route,
        startDestination = ShoppingCartRoutes.ShoppingCartProducts.route
    ) {
        composable(route = ShoppingCartRoutes.ShoppingCartProducts.route) {
            val viewModel: ShoppingCartViewModel = viewModel(
                factory = ShoppingCartViewModel.provideFactory(
                    navigator = navigator
                )
            )

            ShoppingCartScreen(viewModel)
        }
    }
}

sealed class ShoppingCartRoutes(route: String) : Route(route) {
    data object ShoppingCartProducts : ShoppingCartRoutes("shopping_cart")
}