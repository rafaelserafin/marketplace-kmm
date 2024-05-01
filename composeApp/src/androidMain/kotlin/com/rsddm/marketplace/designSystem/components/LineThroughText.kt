package com.rsddm.marketplace.designSystem.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

@Composable
fun LineThroughText(modifier: Modifier, text: String, style: TextStyle = LocalTextStyle.current) {
    val lineThroughPrice = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.LineThrough
            )
        ) {
            append(text)
        }
    }

    Text(
        text = lineThroughPrice,
        style = style,
        modifier = modifier
    )
}