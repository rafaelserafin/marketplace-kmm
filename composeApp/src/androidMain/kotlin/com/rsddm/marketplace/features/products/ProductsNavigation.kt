package com.rsddm.marketplace.features.products

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rsddm.marketplace.navigation.getObject
import com.rsddm.marketplace.features.products.detail.ProductDetailScreen
import com.rsddm.marketplace.features.products.detail.ProductDetailViewModel
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

            ProductsHomeScreen(viewModel)
        }

        composable(ProductsRoutes.Search.route) {
            val viewModel: ProductSearchViewModel = viewModel(
                factory = ProductSearchViewModel.provideFactory(
                    it.arguments?.getString("search") ?: "", navigator
                )
            )

            ProductSearchScreen(viewModel)
        }

        composable(ProductsRoutes.Detail.route) {
            val product = it.arguments?.getObject<Product>("product")

            product?.let {
                val viewModel: ProductDetailViewModel = viewModel(
                    factory = ProductDetailViewModel.provideFactory(
                        product,
                        navigator
                    )
                )

                ProductDetailScreen(
                    viewModel.uiState.collectAsState().value,
                    viewModel::onBuyClick,
                    viewModel::onAddToCartClick,
                    viewModel::onProductClick
                )
            }
        }
    }
}

sealed class ProductsRoutes(route: String) : Route(route) {
    data object List : ProductsRoutes("products_list")
    data object Search : ProductsRoutes("products_search/{search}")
    data object Detail : ProductsRoutes("products_detail/{product}")
}