package com.example.pulsesocial.feature.post

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pulsesocial.feature.post.PostContract.Event.OnContentChange
import com.example.pulsesocial.feature.post.PostContract.Event.OnPostClick
import java.io.File

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
        onPostClick = { uri ->
            viewModel.handleEvent(PostContract.Event.OnPostClick(uri))
        }
    )
}

fun Context.createImageFileUri(): Uri {
    val imagePath = File(cacheDir, "images").apply { mkdirs() }
    val tempFile = File.createTempFile("JPEG_", ".jpg", imagePath)
    return FileProvider.getUriForFile(this, "$packageName.fileprovider", tempFile)
}


@Composable
fun PostScreenContent(
    state: PostContract.State,
    onContentChange: (String) -> Unit,
    onPostClick: (Uri?) -> Unit
) {

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galeryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        selectedImageUri = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri = tempCameraUri
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
                        galeryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) {
                    Icon(Icons.Default.Image, contentDescription = "Galeria")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Galeria"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        val uri = context.createImageFileUri()
                        tempCameraUri = uri
                        cameraLauncher.launch(uri)
                    }
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Câmera")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Câmera")
                }
            }

            selectedImageUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Image Preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            OutlinedTextField(
                value = state.content,
                onValueChange = onContentChange,
                placeholder = { Text("Escreva algo interessante aqui...") }
            )

            Button(
                onClick = { onPostClick(selectedImageUri) },
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Publicar")
                }
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