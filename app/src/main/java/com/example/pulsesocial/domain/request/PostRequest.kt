package com.example.pulsesocial.domain.request

data class PostRequest(
    val content: String,
    val imageUrl: String,
    val userId: Long
)
