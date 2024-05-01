package com.rsddm.marketplace.navigation

import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class NavigatorState(open val title: String) {
    data object Setup : NavigatorState("welcome")
    data class Home(override val title: String, val imageRes: Int) : NavigatorState(title)
    data class Navigation(override val title: String) : NavigatorState(title)
}

class Navigator {
    private var _route = MutableStateFlow<String>(AppRoutes.Products.route)
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
        val routeSplit = route.route.split("/")

        _route.value = "${routeSplit.first()}${params.joinToString { "/$it" }}"
    }
}