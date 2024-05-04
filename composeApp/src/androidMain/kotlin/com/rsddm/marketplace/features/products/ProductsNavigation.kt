package com.rsddm.marketplace.features.products

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rsddm.marketplace.navigation.getObject
import com.rsddm.marketplace.features.products.detail.ProductDetailScreen
import com.rsddm.marketplace.features.products.detail.ProductDetailsViewModel
import com.rsddm.marketplace.features.products.list.ProductListViewModel
import com.rsddm.marketplace.features.products.list.ProductsHomeScreen
import com.rsddm.marketplace.features.products.search.ProductSearchScreen
import com.rsddm.marketplace.features.products.search.ProductSearchViewModel
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.Route
import domain.entities.Product

fun NavGraphBuilder.productsNavigation(navigator: Navigator) {
    navigation(
        route = AppRoutes.Products.route,
        startDestination = ProductsRoutes.List.route
    ) {
        composable(ProductsRoutes.List.route) {
            val viewModel: ProductListViewModel = viewModel(
                factory = ProductListViewModel.provideFactory(navigator)
            )

            ProductsHomeScreen(
                viewModel.uiState.collectAsStateWithLifecycle().value,
                viewModel.actionBundle
            )
        }

        composable(ProductsRoutes.Search.route) {
            val viewModel: ProductSearchViewModel = viewModel(
                factory = ProductSearchViewModel.provideFactory(
                    it.arguments?.getString("search") ?: "", navigator
                )
            )

            ProductSearchScreen(
                viewModel.uiState.collectAsStateWithLifecycle().value,
                viewModel.actionBundle
            )
        }

        composable(ProductsRoutes.Detail.route) {
            val product = it.arguments?.getObject<Product>("product")

            product?.let {
                val viewModel: ProductDetailsViewModel = viewModel(
                    factory = ProductDetailsViewModel.provideFactory(
                        product,
                        navigator
                    )
                )

                ProductDetailScreen(
                    viewModel.uiState.collectAsStateWithLifecycle().value,
                    viewModel.actionBundle
                )
            }
        }
    }
}

sealed class ProductsRoutes(route: String) : Route(route) {
    data object List : ProductsRoutes("products_list")
    data object Search : ProductsRoutes("products_search/{search}")
    data object Detail : ProductsRoutes("products_details/{product}")
}