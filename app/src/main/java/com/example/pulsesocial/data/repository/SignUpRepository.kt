package com.example.pulsesocial.data.repository

import com.example.pulsesocial.data.api.PulseSocialApi
import com.example.pulsesocial.domain.request.UserRequest
import com.example.pulsesocial.domain.response.UserResponse
import retrofit2.Response
import javax.inject.Inject

class SignUpRepository @Inject constructor(
    private val api: PulseSocialApi
) {

    suspend fun createUser(userRequest: UserRequest): Response<UserResponse> {
        return api.createUser(userRequest)
    }

}