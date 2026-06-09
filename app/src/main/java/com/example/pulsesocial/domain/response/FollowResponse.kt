package com.example.pulsesocial.domain.response

data class FollowResponse(
    val userId: Long,
    val following: Boolean,
    val followersCount: Long
)
