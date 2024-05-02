package com.rsddm.marketplace.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class NavigatorState(open val title: String) {
    data object Setup : NavigatorState("welcome")
    data class Home(override val title: String, val imageRes: Int) : NavigatorState(title)
    data class Navigation(override val title: String) : NavigatorState(title)
    data class CleanNavigation(override val title: String) : NavigatorState(title)
}

class Navigator {
    private var _route = MutableStateFlow(AppRoutes.Products.route)
    var route: StateFlow<String> = _route.asStateFlow()

    private var _state = MutableStateFlow<NavigatorState>(NavigatorState.Setup)
    var state: StateFlow<NavigatorState> = _state.asStateFlow()

    fun setState(navigatorState: NavigatorState) {
        _state.value = navigatorState
    }

    fun navigate(route: Route) {
        _route.value = route.route
    }

    fun navigate(route: Route, vararg params: String) {
        val raw = route.route.split("/").first()

        _route.value = "$raw${params.joinToString { "/$it" }}"
    }

    inline fun <reified T> navigate(route: Route, `object`: T) {
        val param = Json.encodeToString(`object`)

        navigate(route, param)
    }
}