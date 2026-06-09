package com.example.pulsesocial.domain.request

data class UserRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val imageUrl: String
)
