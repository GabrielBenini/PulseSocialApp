package com.example.pulsesocial.domain.response

data class LikeResponse(
    val postId: Long,
    val liked: Boolean,
    val likesCount: Long
)
