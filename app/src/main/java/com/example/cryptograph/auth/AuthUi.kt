package com.example.cryptograph.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthUi(viewModel: AuthViewModel, onAuthenticationSucceed: () -> Unit) {

    val authUiEffect by viewModel.uiEffect.collectAsState(initial = null)

    LaunchedEffect(authUiEffect) {
        when (authUiEffect) {
            is AuthUiEffect.AuthenticationSucceed -> onAuthenticationSucceed()
            is AuthUiEffect.AuthenticationFailed -> println("registration failed")
            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val (username, setUsername) = rememberSaveable {
            mutableStateOf("")
        }
        val (password, setPassword) = rememberSaveable {
            mutableStateOf("")
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Username")
            },
            value = username,
            onValueChange = setUsername
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = "Password")
            },
            value = password,
            onValueChange = setPassword
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.register(username = username, password = password) }) {
            Text(text = "Register")
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.login(username = username, password = password) }) {
            Text(text = "Login")
        }
    }
}