package com.rsddm.marketplace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rsddm.marketplace.designSystem.components.TopBar
import com.rsddm.marketplace.features.products.productsNavigation
import com.rsddm.marketplace.features.shopping.icon.ShoppingCartIcon
import com.rsddm.marketplace.features.shopping.icon.ShoppingCartIconViewModel
import com.rsddm.marketplace.features.shopping.shoppingNavigation
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState

@Composable
fun App() {
    val navController = rememberNavController()
    val navigator = remember { Navigator() }

    val route = navigator.route.collectAsStateWithLifecycle()

    LaunchedEffect(route.value.routeID) {
        if (route.value.route == AppRoutes.PopBackStack.route) {
            navController.popBackStack()
        } else {
            navController.navigate(route = route.value.route)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(navigator)

        NavHost(navController, startDestination = AppRoutes.Products.route) {
            productsNavigation(navigator)
            shoppingNavigation(navigator)
        }
    }
}

@Composable
private fun AppTopBar(navigator: Navigator) {
    val state = navigator.state.collectAsStateWithLifecycle()

    val shoppingCartIconViewModel: ShoppingCartIconViewModel = viewModel(
        factory = ShoppingCartIconViewModel.provideFactory(
            navigator
        )
    )

    when (state.value) {
        is NavigatorState.Home -> {
            TopBar(
                title = state.value.title,
                image = painterResource(id = (state.value as NavigatorState.Home).imageRes),
                trailingIcon = {
                    ShoppingCartIcon(viewModel())
                }
            )
        }

        is NavigatorState.Navigation -> {
            TopBar(
                title = state.value.title,
                onBackPressed = { navigator.popBackStack() },
                trailingIcon = {
                    ShoppingCartIcon(shoppingCartIconViewModel)
                }
            )
        }

        is NavigatorState.CleanNavigation -> {
            TopBar(
                title = state.value.title,
                onBackPressed = { navigator.popBackStack() }
            )
        }

        else -> { }
    }
}
