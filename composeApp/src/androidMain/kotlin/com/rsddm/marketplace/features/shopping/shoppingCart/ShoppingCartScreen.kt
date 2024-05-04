package com.rsddm.marketplace.features.shopping.shoppingCart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rsddm.marketplace.R
import com.rsddm.marketplace.designSystem.theme.Green
import com.rsddm.marketplace.designSystem.theme.Red
import domain.entities.ShoppingCartProduct
import kotlinx.coroutines.delay

@Composable
fun ShoppingCartScreen(viewModel: ShoppingCartViewModel) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    val title = stringResource(R.string.shopping_cart)
    LaunchedEffect(state.value) {
        viewModel.setupTopBar(title)
    }

    SimpleAnimatedVisibility(state.value is ShoppingCartUIState.Empty) {
        Empty()
    }

    SimpleAnimatedVisibility(state.value is ShoppingCartUIState.FinalizingPurchase) {
        FinalizingPurchase()
    }

    SimpleAnimatedVisibility(state.value is ShoppingCartUIState.OrderSuccess) {
        OrderSuccess()
    }

    SimpleAnimatedVisibility(state.value is ShoppingCartUIState.OrderError) {
        (state.value as? ShoppingCartUIState.OrderError)?.let {
            OrderError(it.error, it.actionName, it.action)
        }
    }

    SimpleAnimatedVisibility(
        state.value is ShoppingCartUIState.CheckIn
    ) {
        (state.value as? ShoppingCartUIState.CheckIn)?.let {
            ShoppingCart(
                it.products,
                it.totalAmount,
                viewModel::updateProductQuantity,
                viewModel::onBuyClick
            )
        }
    }
}

@Composable
private fun SimpleAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) = AnimatedVisibility(
    visible, modifier,
    enter = fadeIn(),
    exit = fadeOut(),
    content = content
)

@Composable
private fun Empty() {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(140.dp)
                .rotate(180f)
                .scale(scaleX = -1f, scaleY = 1f),
            tint = Color.LightGray
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.shopping_cart_empty),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun FinalizingPurchase() {
    val context = LocalContext.current
    val displayMetrics = context.resources.displayMetrics

    val width = displayMetrics.widthPixels.toFloat()
    var animate by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        animate = true
    }

    val translation by animateFloatAsState(
        targetValue = if (animate) width else -width / 2,
        animationSpec = repeatable(
            iterations = 50,
            animation = tween(
                durationMillis = 1000,
                delayMillis = 200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        finishedListener = {
            animate = false
        }, label = ""
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = null,
            modifier = Modifier.size(140.dp)
                .graphicsLayer {
                    translationX = translation
                },
            tint = Color.LightGray,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.finalizingShoppingOrder),
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun OrderSuccess() {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Green)
    ) {

    }
}

@Composable
private fun OrderError(error: String, actionName: String, action: () -> Unit) {

    var timer by remember { mutableIntStateOf(10) }
    LaunchedEffect(key1 = timer) {
        if (timer > 0) {
            delay(1000L)
            timer -= 1
        } else {
            action()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Red)
            .padding(vertical = 80.dp, horizontal = 24.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = stringResource(R.string.ops),
            style = MaterialTheme.typography.displaySmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = error,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = { action() },
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors().copy(
                contentColor = Color.White
            )
        ) {
            Text("$actionName $timer")
        }

    }
}

@Composable
private fun ShoppingCart(
    products: List<ShoppingCartProduct>,
    totalAmount: String,
    updateProductQuantity: (ShoppingCartProduct, Int) -> Unit,
    onBuyClick: () -> Unit
) = Column {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .weight(1f),
        contentPadding = PaddingValues(16.dp)
    ) {

        item {
            Text(
                text = stringResource(R.string.products),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(products) {
            ShoppingCartItem(it, updateProductQuantity)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    Surface(
        shadowElevation = 8.dp
    ) {
        Column(
            modifier = Modifier.height(200.dp)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.cart_summary),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.quantity_of_items),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = products.sumOf { it.quantity }.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.total_amount),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = totalAmount,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { onBuyClick() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.finalize_purchase))
                }
            }
        }
    }


}

@Composable
private fun ShoppingCartItem(
    product: ShoppingCartProduct,
    updateProductQuantity: (ShoppingCartProduct, Int) -> Unit
) {
    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(modifier = Modifier.fillMaxHeight()) {
                Image(
                    painter = painterResource(id = R.drawable.temp_product),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .background(MaterialTheme.colorScheme.background)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(4.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = product.price,
                        style = MaterialTheme.typography.titleSmall,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .height(32.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(2.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Button(
                            onClick = { updateProductQuantity(product, -1) },
                            modifier = Modifier.size(32.dp),
                            colors = ButtonDefaults.buttonColors().copy(
                                contentColor = Color.Black,
                                containerColor = Color.LightGray
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(topStart = 2.dp, bottomStart = 2.dp)
                        ) {
                            Text(
                                text = "-",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            text = product.quantity.toString(),
                            modifier = Modifier.width(32.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Button(
                            onClick = { updateProductQuantity(product, +1) },
                            modifier = Modifier.size(32.dp),
                            colors = ButtonDefaults.buttonColors().copy(
                                contentColor = Color.Black,
                                containerColor = Color.LightGray
                            ),
                            contentPadding = PaddingValues(0.dp),
                            shape = RoundedCornerShape(topEnd = 2.dp, bottomEnd = 2.dp)
                        ) {
                            Text(
                                text = "+",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                    }
                }

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(48.dp)
                        .padding(12.dp)
                        .clickable {
                            updateProductQuantity(product, -product.quantity)
                        }
                        .clip(CircleShape)
                )
            }
        }
    }
}