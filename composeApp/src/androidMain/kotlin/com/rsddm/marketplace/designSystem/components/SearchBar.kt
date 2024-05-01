package com.rsddm.marketplace.designSystem.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

typealias OnSearch = (String) -> Unit

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String,
    initialText: String = "",
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    backgroundContrast: Color = MaterialTheme.colorScheme.onPrimary,
    onSearch: OnSearch,
) {
    var text by remember { mutableStateOf(initialText) }

    Surface(
        modifier = Modifier.fillMaxWidth()
            .background(backgroundColor)
            .padding(start = 12.dp, top = 8.dp, end = 12.dp, bottom = 16.dp),
        shadowElevation = 4.dp
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = modifier
                .fillMaxWidth()
                .background(backgroundColor),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(hint) },
            colors = TextFieldDefaults.colors().copy(
                unfocusedContainerColor = backgroundContrast,
                focusedContainerColor = backgroundContrast,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = hint,
                    tint = Color.Gray,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                )
            },
            keyboardActions = KeyboardActions(
                onSearch = {
                    if (text.isNotBlank()) {
                        onSearch(text)
                    }
                }
            ),
        )
    }
}