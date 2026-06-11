package com.example.pulsesocial.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.pulsesocial.feature.login.LoginContract.Event.OnEmailChange
import com.example.pulsesocial.feature.login.LoginContract.Event.OnPassChange
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginClicked: () -> Unit,
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { event ->
            when (event) {
                is LoginContract.Effect.OnLoginSuccess -> {
                    onLoginClicked()
                }

                is LoginContract.Effect.OnLoginFailure -> {
                    // mostrar erro
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxSize()
    ) {

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.handleEvent(OnEmailChange(it)) },
            placeholder = { Text("Digite seu email") }
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.handleEvent(OnPassChange(it)) },
            placeholder = { Text("Digite sua senha") }
        )


        Button(
            onClick = {
                viewModel.handleEvent(LoginContract.Event.OnLoginClick)
            },
            enabled = !state.isLoading
        ) {

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(20.dp),
                    strokeWidth = 2.dp,
                )
            } else {

                Text("Login")
            }
        }
    }

}