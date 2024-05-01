package com.rsddm.marketplace

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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

    val route = navigator.route.collectAsState()
    val state = navigator.state.collectAsState()

    LaunchedEffect(route) {
        navController.navigate(route = route.value.route)
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
//
//@Composable
//fun TopBar() {
//    Surface(
//        color = MaterialTheme.colorScheme.primary,
//        shadowElevation = 4.dp
//    ) {
//        Column(
//            modifier = Modifier.fillMaxWidth()
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.user_logo),
//                    contentDescription = "User Logo",
//                    modifier = Modifier
//                        .size(48.dp)
//                        .clip(RoundedCornerShape(24.dp))
//                        .background(MaterialTheme.colorScheme.onPrimary)
//                        .padding(6.dp)
//                )
//
//                Spacer(modifier = Modifier.width(16.dp))
//
//                Text(
//                    text = "Bem vindo, \nRafael Serafin",
//                    style = MaterialTheme.typography.titleMedium,
//                    modifier = Modifier.weight(1f)
//                )
//
//                ShoppingCartIcon(viewModel())
//            }
//
//            TextField(
//                value = "",
//                onValueChange = { },
//                placeholder = { Text("Pesquisar") },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(8.dp)),
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Search
//                ),
//                trailingIcon = {
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "Pesquisar",
//                        tint = Color.Gray,
//                        modifier = Modifier
//                            .padding(8.dp)
//                            .size(24.dp)
//                    )
//                },
//                keyboardActions = KeyboardActions(
//                    onSearch = {
//                        // Ação de pesquisa
//                        // Implemente a lógica de pesquisa aqui
//                    }
//                ),
//                colors = TextFieldDefaults.colors().copy(
//                    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
//                    focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
//                    focusedIndicatorColor = Color.Transparent,
//                    unfocusedIndicatorColor = Color.Transparent,
//                    disabledIndicatorColor = Color.Transparent,
//                )
//            )
//        }
//    }
//}

