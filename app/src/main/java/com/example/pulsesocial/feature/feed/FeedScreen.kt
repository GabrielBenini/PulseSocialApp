package com.example.pulsesocial.feature.feed

import android.view.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import com.example.pulsesocial.feature.navigation.AppNavigation

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

                is FeedContract.Effect.NavigateToComments -> {
                    navController.navigate("${AppNavigation.CommentsScreen}/${effect.postId}")
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
        onCommentClick = { postId ->
            viewModel.handleEvent(FeedContract.Event.OnCommentClick(postId))
        },
    )
}

@Composable
fun FeedScreenContent(
    state: FeedContract.State,
    onLikeClick: (Long) -> Unit,
    onCommentClick: (Long) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),

    ) {

        LazyColumn(
            modifier = Modifier.padding(),
            verticalArrangement = Arrangement.Top
        ) {

            items(
                items = state.posts,
                key = { it.id }
            ) { post ->

                PostCard(
                    post = post,
                    onLikeClick = { onLikeClick(post.id) },
                    onCommentClick = { onCommentClick(post.id) }
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
        onCommentClick = {},
    )
}