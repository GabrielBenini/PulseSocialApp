package com.example.pulsesocial.feature.comments

import com.example.pulsesocial.domain.response.CommentResponse

object CommentContract {

    data class State(
        val comments: List<CommentResponse> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class Event {
        data class LoadComments(val postId: Long) : Event()
        data class SendComment(val postId: Long, val content: String) : Event()
    }

    sealed class Effect {
        data class ShowError(val message: String) : Effect()
    }
}