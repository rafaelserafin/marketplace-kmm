package com.rsddm.marketplace.features.profile.login

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.rsddm.marketplace.R
import com.rsddm.marketplace.designSystem.components.Loading
import domain.entities.UserLogin
import kotlin.math.sin

@Composable
fun ProfileLoginScreen(
    uiState: ProfileLogin.UIState,
    actionBundle: ProfileLogin.ActionBundle
) {

    val title = stringResource(R.string.login)
    LaunchedEffect(true) {
        actionBundle.setupTopBar(title)
    }

    when (uiState) {
        ProfileLogin.UIState.Loading -> Loading()
        ProfileLogin.UIState.Idle -> Login(null, null, actionBundle)
        is ProfileLogin.UIState.Error -> Login(uiState.userLogin, uiState.message, actionBundle)
    }
}

@Composable
private fun Login(userLogin: UserLogin?, error: String?, actionBundle: ProfileLogin.ActionBundle) {
    Column(
        modifier = Modifier.fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 40.dp),
        verticalArrangement = Arrangement.Top
    ) {
        val context = LocalContext.current

        var username by remember { mutableStateOf(userLogin?.username ?: "test@test.com") }
        var password by remember { mutableStateOf(userLogin?.password ?: "123456") }

        LaunchedEffect(error) {
            error?.let {
                Toast.makeText(context, "Ops, não foi possível efetuar o login, verifique seu e-mail e senha", Toast.LENGTH_LONG).show()
            }
        }

        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(value = username,
            onValueChange = { username = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Email")
            })

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text("Senha")
            },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { actionBundle.login(username, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
    }
}