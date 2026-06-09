package com.example.pulsesocial.domain.response

import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val imageUrl: String,
    val createdAt: LocalDateTime
)
