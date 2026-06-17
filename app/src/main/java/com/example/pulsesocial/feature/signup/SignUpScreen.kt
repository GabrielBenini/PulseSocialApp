package com.example.pulsesocial.feature.signup

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
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

            when(event) {

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
        onImageChange = {viewModel.handleEvent(SignUpContract.Event.OnImageChange(it))}
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


    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .safeContentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

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

                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Icon(
                        modifier = Modifier.padding(14.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                    )
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
            label = { Text("Digite seu nome completo") }
        )

        SignUpTextField(
            modifier = modifier.padding(bottom = 8.dp),
            value = state.email,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Name Field Icon"
                )
            },
            onValueChange = { onEmailChange(it) },
            label = { Text("Digite seu melhor e-mail") }
        )

        SignUpTextField(
            modifier = modifier.padding(bottom = 8.dp),
            value = state.password,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Name Field Icon"
                )
            },
            onValueChange = { onPasswordChange(it) },
            label = { Text("Crie uma senha segura") }
        )

        SignUpTextField(
            modifier = modifier.padding(bottom = 8.dp),
            value = state.confirmPassword,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Name Field Icon"
                )
            },
            onValueChange = { onConfirmPasswordChange(it) },
            label = { Text("Confirme sua senha") }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onCreateClick() }
        ) {
            Text("Criar Conta")
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
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onCreateClick = {},
            onImageChange = {}
        )
    }
}