package com.rsddm.marketplace.features.profile.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.R

@Composable
fun ProfileLoginScreen(
    uiState: ProfileLoginUIState,
    setupTopBar: (String) -> Unit
) {

    val title = stringResource(R.string.login)
    LaunchedEffect(true) {
        setupTopBar(title)
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Usuário")
            })

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Senha")
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}