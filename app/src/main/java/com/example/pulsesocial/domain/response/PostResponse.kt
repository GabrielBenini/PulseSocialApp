package com.example.pulsesocial.domain.response

import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val content: String,
    val imageUrl: String,
    val createdAt: String,
    val userId: Long,
    val likesCount: Int,
    val commentsCount: Int,
    val likedByUser: Boolean
)
