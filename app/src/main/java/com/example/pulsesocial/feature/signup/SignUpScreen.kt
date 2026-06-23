package com.example.pulsesocial.feature.signup

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.pulsesocial.R
import com.example.pulsesocial.feature.components.SignUpTextField
import com.example.pulsesocial.ui.theme.PulseSocialTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.uiEffect.collectLatest { event ->
            Log.d("NAVIGATION", "Evento recebido: $event")

            when (event) {

                is SignUpContract.Effect.OnSignUpSuccess -> {
                    onNavigateToLogin()
                }

                is SignUpContract.Effect.OnSignUpFailure -> {
                    //todo
                }
            }
        }
    }

    SignUpScreenContent(
        state = state,
        onUsernameChange = { viewModel.handleEvent(SignUpContract.Event.OnUsernameChange(it)) },
        onCreateClick = { viewModel.handleEvent(SignUpContract.Event.OnCreateClick) },
        onEmailChange = { viewModel.handleEvent(SignUpContract.Event.OnEmailChange(it)) },
        onPasswordChange = { viewModel.handleEvent(SignUpContract.Event.OnPasswordChange(it)) },
        onConfirmPasswordChange = {
            viewModel.handleEvent(
                SignUpContract.Event.OnConfirmPasswordChange(
                    it
                )
            )
        },
        onImageChange = { viewModel.handleEvent(SignUpContract.Event.OnImageChange(it)) },
        onTogglePassword = { viewModel.handleEvent(SignUpContract.Event.TooglePasswordVisibility) },
        onNavigateToLogin = { onNavigateToLogin() }
    )

}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    state: SignUpContract.State,
    onUsernameChange: (String) -> Unit,
    onCreateClick: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onImageChange: (Uri?) -> Unit,
    onNavigateToLogin: () -> Unit,
    onTogglePassword: () -> Unit,
) {

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri
        uri?.let {
            onImageChange(it)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(R.drawable.app_background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        LazyColumn(
            modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                Text(
                    "Pulse",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    "Criar sua conta",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                )

                Text(
                    text = "Junte-se a comunidade e comece",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = "a compartilhar com o mundo",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )

            }

            item {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                imagePicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                        )
                ) {

                    if (selectedImageUri == null) {

                        Box(
                            contentAlignment = Alignment.Center
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(110.dp)
                                    .shadow(
                                        elevation = 20.dp,
                                        shape = CircleShape,
                                        ambientColor = MaterialTheme.colorScheme.primary,
                                        spotColor = MaterialTheme.colorScheme.primary
                                    )
                            )

                            Surface(
                                modifier = Modifier.size(100.dp),
                                shape = CircleShape,
                                border = BorderStroke(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                                color = MaterialTheme.colorScheme.surface
                            ) {
                                Icon(
                                    modifier = Modifier.padding(14.dp),
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = "Avatar"
                                )
                            }
                        }
                    } else {

                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Imagem do usuario",
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .size(28.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Adicionar imagem",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            item {

                SignUpTextField(
                    modifier = modifier.padding(top = 16.dp),
                    value = state.username,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.PersonOutline,
                            contentDescription = "Name Field Icon"
                        )
                    },
                    onValueChange = { onUsernameChange(it) },
                    label = { Text("Digite seu nome completo") },
                    errorMessage = state.usernameError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                SignUpTextField(
                    value = state.email,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = "Name Field Icon"
                        )
                    },
                    onValueChange = { onEmailChange(it) },
                    label = { Text("Digite seu melhor e-mail") },
                    errorMessage = state.emailError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                SignUpTextField(
                    value = state.password,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Name Field Icon"
                        )
                    },
                    onValueChange = { onPasswordChange(it) },
                    label = { Text("Crie uma senha segura") },
                    visualTransformation = if (state.showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onTogglePassword() }
                        ) {
                            Icon(
                                if (state.showPassword)
                                    Icons.Outlined.Visibility
                                else
                                    Icons.Outlined.VisibilityOff,
                                contentDescription = "Show pass"
                            )
                        }
                    },
                    errorMessage = state.passwordError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    )
                )

                SignUpTextField(
                    value = state.confirmPassword,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Name Field Icon"
                        )
                    },
                    onValueChange = { onConfirmPasswordChange(it) },
                    label = { Text("Crie uma senha segura") },
                    visualTransformation = if (state.showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(
                            onClick = { onTogglePassword() }
                        ) {
                            Icon(
                                if (state.showPassword)
                                    Icons.Outlined.Visibility
                                else
                                    Icons.Outlined.VisibilityOff,
                                contentDescription = "Show pass"
                            )
                        }
                    },
                    errorMessage = state.confirmPasswordError,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    )
                )
            }

            item {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = Icons.Outlined.Security,
                        contentDescription = ("Pass Conditions")
                    )

                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text(
                            "A senha deve conter",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            "• Mínimo de 8 caracteres\n• Mínimo de 1 número\n• Mínimo de 1 letra maiúscula",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            item {

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onCreateClick()
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
                        Row {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(20.dp),
                                strokeWidth = 2.dp,
                            )
                        }
                    } else {
                        Text("Criar Conta")
                    }
                }
            }

            item {

                Row(
                    modifier = Modifier
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Já tem uma conta?",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    TextButton(
                        onClick = {
                            onNavigateToLogin()
                        }
                    ) {
                        Text(
                            "Entrar",
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenContentPreview() {
    PulseSocialTheme {
        SignUpScreenContent(
            state = SignUpContract.State(),
            onUsernameChange = {},
            onCreateClick = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onImageChange = {},
            onNavigateToLogin = {},
            onTogglePassword = {}
        )
    }
}