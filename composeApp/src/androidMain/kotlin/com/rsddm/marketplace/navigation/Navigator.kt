package com.rsddm.marketplace.navigation

import com.rsddm.marketplace.features.products.ProductsRoutes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class NavigatorState(open val title: String) {
    data object None : NavigatorState("welcome")
    data class Home(override val title: String, val imageRes: Int) : NavigatorState(title)
    data class Navigation(override val title: String) : NavigatorState(title)
    data class CleanNavigation(override val title: String) : NavigatorState(title)
}

class Navigator {
    private var _route = MutableStateFlow<Route>(AppRoutes.Products)
    var route: StateFlow<Route> = _route.asStateFlow()

    private var _state = MutableStateFlow<NavigatorState>(NavigatorState.None)
    var state: StateFlow<NavigatorState> = _state.asStateFlow()

    fun setState(navigatorState: NavigatorState) {
        _state.value = navigatorState
    }

    fun navigate(route: Route) {
        // Generate a new RouteId
        navigateAndPop(route, _route.value)
        //_route.value = object : Route(route.route) {}
    }

    fun navigateAndPop(route: Route, from: Route) {
        // Generate a new RouteId
        _route.value = object : Route(route.route) {
            override val from: Route
                get() = from
        }
    }

    fun navigate(route: Route, vararg params: String) {
        val raw = route.route.split("/").first()

        // Generate a new RouteId
        _route.value = object : Route("$raw${params.joinToString { "/$it" }}") {}
    }

    inline fun <reified T> navigate(route: Route, `object`: T) {
        val param = Json.encodeToString(`object`)

        navigate(route, param)
    }

    fun navigateBack() {
        _route.value.from?.let {
            _route.value = it
        }
    }

    fun popBackStack() {
        // Generate a new RouteId
        _route.value = object : Route(AppRoutes.PopBackStack.route) {}
    }
}