package com.example.pulsesocial.feature.feed

import com.example.pulsesocial.domain.response.PostResponse

object FeedContract {

    data class State(
        val posts: List<PostResponse> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class Event {

        object LoadPosts: Event()
        object OnRefresh: Event()
        data class OnLikeClick(val postId: Long): Event()
        data class OnCommentClick(val postId: Long): Event()
    }

    sealed class Effect {

        data class ShowError(val message: String): Effect()
        data class NavigateToComments(val postId: Long): Effect()
    }
}