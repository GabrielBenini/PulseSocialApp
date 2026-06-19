package com.example.pulsesocial.feature.post

import android.R.attr.contentDescription
import android.R.attr.tint
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material.icons.outlined.HideImage
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.pulsesocial.R
import com.example.pulsesocial.domain.response.UserSummary
import com.example.pulsesocial.feature.components.PostButton
import com.example.pulsesocial.feature.components.PostTextField
import com.example.pulsesocial.feature.components.SignUpTextField
import com.example.pulsesocial.feature.post.PostContract.Event.OnContentChange
import com.example.pulsesocial.feature.post.PostContract.Event.OnPostClick
import java.io.File

@Composable
fun PostScreen(
    navController: NavController,
    viewModel: PostViewModel = hiltViewModel(),
    onCloseButton: () -> Unit
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
        onPostClick = { uri -> viewModel.handleEvent(OnPostClick(uri)) },
        onCloseButton = { onCloseButton() }
    )
}

fun Context.createImageFileUri(): Uri {
    val imagePath = File(cacheDir, "images").apply { mkdirs() }
    val tempFile = File.createTempFile("JPEG_", ".jpg", imagePath)
    return FileProvider.getUriForFile(this, "$packageName.fileprovider", tempFile)
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostScreenContent(
    state: PostContract.State,
    onContentChange: (String) -> Unit,
    onPostClick: (Uri?) -> Unit,
    onCloseButton: () -> Unit,
) {

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
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

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    windowInsets = WindowInsets(0.dp),
                    title = {
                        Text(
                            "Novo Post",
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = { onCloseButton() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "CloseButton",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {
                        Button(
                            shape = RoundedCornerShape(14.dp),
                            onClick = { onPostClick(selectedImageUri) },
                            enabled = !state.isLoading,
                            colors = ButtonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContainerColor = MaterialTheme.colorScheme.outline
                            )

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
                )
            }
        ) { contentPadding ->

            Image(
                painter = painterResource(R.drawable.app_feed_background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PostTextField(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .height(300.dp),
                    value = state.content,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = onContentChange,
                    label = {
                        Text(
                            "No que você está pensando?",
                            textAlign = TextAlign.Start,
                        )
                    },
                )

                PostButton(
                    onGalleryClick = {
                        galleryLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    onCameraClick = {
                        val uri = context.createImageFileUri()
                        tempCameraUri = uri
                        cameraLauncher.launch(uri)
                    }
                )

                Surface(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                    )
                ) {

                    if (selectedImageUri == null) {

                        Column(
                            modifier = Modifier
                                .height(300.dp)
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Icon(
                                modifier = Modifier
                                    .padding(bottom = 12.dp)
                                    .size(50.dp),
                                imageVector = Icons.Outlined.HideImage,
                                contentDescription = "Image",
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                "Nenhuma mídia selecionada",
                                fontWeight = FontWeight.SemiBold
                            )

                            Text(
                                "Adicione uma foto ao seu post",
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
                            )
                        }

                    } else

                        selectedImageUri?.let { uri ->
                            AsyncImage(
                                model = uri,
                                contentDescription = "Image Preview",
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }
        }
    }

@Preview()
@Composable
fun PostScreenPreview() {

    PostScreenContent(
        state = PostContract.State(
            content = ""
        ),
        onContentChange = {},
        onPostClick = {},
        onCloseButton = {}
    )
}