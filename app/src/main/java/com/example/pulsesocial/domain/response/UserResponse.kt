package com.example.pulsesocial.domain.response

data class UserResponse(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val imageUrl: String?,
    val createdAt: String,
    val followersCount: Long,
    val followingCount: Long
)
