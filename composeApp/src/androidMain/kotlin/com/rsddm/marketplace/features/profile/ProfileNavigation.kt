package com.rsddm.marketplace.features.profile

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rsddm.marketplace.features.profile.detail.ProfileDetailScreen
import com.rsddm.marketplace.features.profile.detail.ProfileDetailViewModel
import com.rsddm.marketplace.features.profile.login.ProfileLoginScreen
import com.rsddm.marketplace.features.profile.login.ProfileLoginViewModel
import com.rsddm.marketplace.navigation.AppRoutes
import com.rsddm.marketplace.navigation.Navigator
import com.rsddm.marketplace.navigation.Route
import data.session.UserSession

fun NavGraphBuilder.profileNavigation(navigator: Navigator) {
    navigation(
        route = AppRoutes.Profile.route,
        startDestination = if (UserSession.user == null) ProfileRoutes.Login.route else ProfileRoutes.Profile.route
    ) {
        composable(ProfileRoutes.Profile.route) {
            val viewModel: ProfileDetailViewModel = viewModel(
                factory = ProfileDetailViewModel.provideFactory(navigator)
            )

            ProfileDetailScreen(
                viewModel.uiState.collectAsStateWithLifecycle().value,
                viewModel.actionBundle
            )
        }

        composable(ProfileRoutes.Login.route) {
            val viewModel: ProfileLoginViewModel = viewModel(
                factory = ProfileLoginViewModel.provideFactory(navigator)
            )

            ProfileLoginScreen(
                viewModel.uiState.collectAsStateWithLifecycle().value,
                viewModel.actionBundle
            )
        }
    }
}

sealed class ProfileRoutes(route: String) : Route(route) {
    data object Login : ProfileRoutes("profile_login")
    data object Profile : ProfileRoutes("profile_detail")
}