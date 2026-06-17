package com.example.pulsesocial.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.pulsesocial.R
import com.example.pulsesocial.feature.components.SignUpTextField
import com.example.pulsesocial.feature.login.LoginContract.Event.OnEmailChange
import com.example.pulsesocial.feature.login.LoginContract.Event.OnPassChange

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginClicked: () -> Unit,
    onSignUpClick: () -> Unit,
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

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.app_background_image),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {

            Text(
                "Pulse",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 126.dp, bottom = 16.dp)
            )

            Text(
                "Bem-vindo de volta!",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                "Entre para continuar conectando",
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                "ideias e pessoas",
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
                modifier = Modifier.padding(bottom = 36.dp)
            )



            SignUpTextField(
                value = state.email,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Login Icon"
                    )
                },
                onValueChange = { viewModel.handleEvent(OnEmailChange(it)) },
                label = { Text("Digite seu e-mail") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            SignUpTextField(
                value = state.password,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Pass Icon"
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            viewModel.handleEvent(LoginContract.Event.TogglePasswordVisibility)
                        }
                    ) {
                        Icon(
                            imageVector = if (state.showPassword)
                                Icons.Outlined.Visibility
                            else
                                Icons.Outlined.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                onValueChange = { viewModel.handleEvent(OnPassChange(it)) },
                label = { Text("Digite sua senha") },
                visualTransformation = if (state.showPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )


            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = {
                    viewModel.handleEvent(LoginContract.Event.OnLoginClick)
                },
                enabled = !state.isLoading,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.outline
                ),
            ) {

                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp,
                    )
                } else {

                    Text("Entrar")
                }
            }

            TextButton(
                onClick = {},
                modifier = Modifier
                    .align(alignment = Alignment.End)
            ) {
                Text("Esqueceu sua senha?")
            }
        }

        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Ainda não tem uma conta?",
                fontSize = 14.sp
            )

            TextButton(
                onClick = { onSignUpClick() }
            ) {
                Text(
                    "Cadastre-se",
                    fontSize = 14.sp
                )

            }
        }


    }
}