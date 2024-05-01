package com.rsddm.marketplace.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rsddm.marketplace.R
import com.rsddm.marketplace.features.shoppingCart.ShoppingCartIcon

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Top Bar
        TopBar()

        // User Info Section
        UserInfoSection()

        // Product Carousel
        ProductCarousel()
    }
}

@Composable
fun TopBar() {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user_logo),
                    contentDescription = "User Logo",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(6.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Bem vindo, \nRafael Serafin",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )

                ShoppingCartIcon(viewModel())
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                TextField(
                    value = "",
                    onValueChange = { },
                    placeholder = { Text("Pesquisar") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            // Ação de pesquisa
                            // Implemente a lógica de pesquisa aqui
                        }
                    )
                )


                // Ícone de pesquisa
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar",
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun UserInfoSection() {
    // Seção de informações do usuário (pode ser adicionado aqui)
}

@Composable
fun ProductCarousel() {
    // Lista de produtos em um carousel (pode ser adicionado aqui)
}
