package com.rsddm.marketplace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.rsddm.marketplace.designSystem.components.TopBar
import com.rsddm.marketplace.features.products.productsNavigation
import com.rsddm.marketplace.features.profile.profileNavigation
import com.rsddm.marketplace.features.shopping.icon.ShoppingCartIcon
import com.rsddm.marketplace.features.shopping.icon.ShoppingCartIconViewModel
import com.rsddm.marketplace.features.shopping.shoppingNavigation
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.NavigatorState
import session.Session

@Composable
fun App() {
    val navController = rememberNavController()
    val navigator = remember { Navigator() }

    val route = navigator.route.collectAsStateWithLifecycle()

    LaunchedEffect(route.value.routeID) {
        if (route.value.route == AppRoutes.PopBackStack.route) {
            navController.popBackStack()
        } else {
            navController.navigate(route = route.value.route) {
                route.value.from?.let {
                    popUpTo(it.route)
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(navigator)

        NavHost(navController, startDestination = AppRoutes.Products.route) {
            profileNavigation(navigator)
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
                title = if (Session.userSession == null) "Acessar conta" else "Olá, ${Session.userSession?.name}",
                textUnderline = true,
                image = painterResource(id = (state.value as NavigatorState.Home).imageRes),
                onTitleClick = {
                    navigator.navigate(AppRoutes.Profile)
                },
                trailingIcon = {
                    ShoppingCartIcon(
                        shoppingCartIconViewModel.uiState.collectAsStateWithLifecycle().value,
                        shoppingCartIconViewModel.actionBundle
                    )
                }
            )
        }

        is NavigatorState.Navigation -> {
            TopBar(
                title = state.value.title,
                onBackPressed = { navigator.popBackStack() },
                trailingIcon = {
                    ShoppingCartIcon(
                        shoppingCartIconViewModel.uiState.collectAsStateWithLifecycle().value,
                        shoppingCartIconViewModel.actionBundle
                    )
                }
            )
        }

        is NavigatorState.CleanNavigation -> {
            TopBar(
                title = state.value.title,
                onBackPressed = { navigator.popBackStack() }
            )
        }

        else -> {}
    }
}
