package com.example.pulsesocial.domain.response

import android.net.Uri
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val imageUrl: String?,
    val createdAt: String
)
