package com.rsddm.marketplace.features.shopping.icon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.R

@Composable
fun ShoppingCartIcon(
    uiState: ShoppingCartIcon.UIState,
    actionBundle: ShoppingCartIcon.ActionBundle
) {

    var animate by remember { mutableStateOf(false) }
    val badgeCount = uiState.count

    LaunchedEffect(badgeCount) {
        animate = true
    }

    Box(
        modifier = Modifier.size(48.dp)
            .clickable { actionBundle.onShoppingCartClick() },
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = stringResource(R.string.shopping_cart),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .padding(12.dp)
                .size(24.dp)
        )

        val scale by animateFloatAsState(
            targetValue = if (animate) 1.3f else 1f,
            animationSpec = repeatable(
                iterations = 1,
                animation = tween(durationMillis = 200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            finishedListener = {
                animate = false
            }, label = ""
        )

        AnimatedVisibility(
            badgeCount > 0,
            enter = scaleIn(animationSpec = tween(50))
        ) {
            Text(
                text = "$badgeCount",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier
                    .size(18.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
                    .background(
                        MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}