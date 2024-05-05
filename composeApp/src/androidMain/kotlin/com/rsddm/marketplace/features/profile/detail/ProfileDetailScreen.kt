package com.rsddm.marketplace.features.profile.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.R
import com.rsddm.marketplace.designSystem.components.Loading
import domain.entities.ShoppingOrder
import domain.entities.UserProfile

@Composable
fun ProfileDetailScreen(
    uiState: ProfileDetail.UIState,
    actionBundle: ProfileDetail.ActionBundle
) {

    val title = stringResource(R.string.profile_detail_title)
    LaunchedEffect(true) {
        actionBundle.setupTopBar(title)
    }

    when (uiState) {
        is ProfileDetail.UIState.Detail -> ProfileDetail(
            uiState.userProfile,
            uiState.orders,
            actionBundle::logout
        )

        ProfileDetail.UIState.Loading -> Loading()
    }
}

@Composable
private fun ProfileDetail(
    userProfile: UserProfile, orders: List<ShoppingOrder>,
    logout: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.profile_data),
            style = MaterialTheme.typography.titleMedium
        )
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "${stringResource(R.string.name)}: ${userProfile.name}")
        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "${stringResource(R.string.birthday)}: ${userProfile.birthday}")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { logout() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.logout))
        }
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.my_orders),
            style = MaterialTheme.typography.titleMedium
        )

        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(orders) {
                OrderItem(it)
            }
        }
    }
}

@Composable
private fun OrderItem(shoppingOrder: ShoppingOrder) {
    Surface(
        shadowElevation = 4.dp,
        modifier = Modifier
            .padding(vertical = 4.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Column(modifier = Modifier.padding(8.dp)) {
                Box(
                    modifier = Modifier.size(70.dp),
                    contentAlignment = Alignment.BottomEnd
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.temp_product),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .background(MaterialTheme.colorScheme.background)
                    )

                    if (shoppingOrder.products.count() > 1) {
                        Text(
                            text = "+ ${shoppingOrder.products.count() - 1}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(2.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(70.dp)
            ) {
                Text(
                    text = "${stringResource(R.string.order)} #${shoppingOrder.createdAt}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(text = shoppingOrder.totalAmount)
            }
        }
    }
}