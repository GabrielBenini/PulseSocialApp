package com.example.pulsesocial.domain.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdAt: String,
    val author: UserSummary,
    val postId: Long
)
