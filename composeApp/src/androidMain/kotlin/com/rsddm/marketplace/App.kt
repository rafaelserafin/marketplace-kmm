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
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rsddm.marketplace.designSystem.components.TopBar
import com.rsddm.marketplace.features.products.productsNavigation
import com.rsddm.marketplace.features.shoppingCart.ShoppingCartIcon
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navigator = remember { Navigator() }

    val route = navigator.route.collectAsStateWithLifecycle()
    val state = navigator.state.collectAsStateWithLifecycle()

    LaunchedEffect(route.value) {
        navController.navigate(route = route.value)
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                    onBackPressed = { navController.popBackStack() },
                    trailingIcon = {
                        ShoppingCartIcon(viewModel())
                    }
                )
            }

            else -> {}
        }

        NavHost(navController, startDestination = AppRoutes.Setup.route) {
            composable(route = AppRoutes.Setup.route) { }
            productsNavigation(navigator)
        }

    }
}
