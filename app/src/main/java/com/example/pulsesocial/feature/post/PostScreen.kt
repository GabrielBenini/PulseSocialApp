package com.example.pulsesocial.feature.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pulsesocial.feature.navigation.AppNavigation
import com.example.pulsesocial.feature.post.PostContract.Event.OnContentChange
import com.example.pulsesocial.feature.post.PostContract.Event.OnPostClick

@Composable
fun PostScreen(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is PostContract.Effect.ShowSuccess -> {
                    navController.popBackStack()
                }

                is PostContract.Effect.ShowError -> {
                    // mostrar erro
                }

            }
        }
    }

    PostScreenContent(
        state = state,
        onContentChange = { viewModel.handleEvent(OnContentChange(it)) },
        onPostClick = { viewModel.handleEvent(OnPostClick) }
    )
}


@Composable
fun PostScreenContent(
    state: PostContract.State,
    onContentChange: (String) -> Unit,
    onPostClick: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = state.content,
                onValueChange = onContentChange,
                placeholder = { Text("Escreva algo interessante aqui...") }
            )

            Button(
                onClick = onPostClick
            ) {
                Text("Publicar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PostScreenPreview() {

    PostScreenContent(
        state = PostContract.State(
            content = "Post de teste"
        ),
        onContentChange = {},
        onPostClick = {}
    )
}