package com.example.pulsesocial.domain.request

data class CommentRequest(
    val content: String,
    val postId: Long,
    val userId: Long
)
