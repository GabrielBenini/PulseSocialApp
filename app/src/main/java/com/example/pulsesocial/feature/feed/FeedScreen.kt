package com.example.pulsesocial.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pulsesocial.feature.components.PostCard

@Composable
fun FeedScreen(
    navController: NavController,
    viewModel: FeedViewModel = hiltViewModel(),
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.handleEvent(FeedContract.Event.LoadPosts)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {

                is FeedContract.Effect.ShowSuccess -> {
                    // futuramente snackbar
                }

                is FeedContract.Effect.ShowError -> {
                    // futuramente snackbar
                }
            }
        }
    }

    FeedScreenContent(
        state = state,
        onLikeClick = { postId ->
            viewModel.handleEvent(FeedContract.Event.OnLikeClick(postId))
        },

        onDeletePostClick = { postId ->
            viewModel.handleEvent(FeedContract.Event.OnDeletePostClick(postId))
        }
    )
}

@Composable
fun FeedScreenContent(
    state: FeedContract.State,
    onLikeClick: (Long) -> Unit,
    onDeletePostClick: (Long) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colorScheme.background

    ) {

        LazyColumn(
            verticalArrangement = Arrangement.Top
        ) {

            items(
                items = state.posts,
                key = { it.id }
            ) { post ->

                PostCard(
                    post = post,
                    currentUserId = state.currentUserId ?: -1,
                    onLikeClick = { onLikeClick(post.id) },
                    onDeletePostClick = {onDeletePostClick(post.id)}
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedPreview() {

    FeedScreenContent(
        state = FeedContract.State(posts = emptyList()),
        onLikeClick = {},
        onDeletePostClick = {}
    )
}