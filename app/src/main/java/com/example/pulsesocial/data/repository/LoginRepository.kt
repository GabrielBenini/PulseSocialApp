package com.example.pulsesocial.data.repository

import com.example.pulsesocial.data.api.PulseSocialApi
import com.example.pulsesocial.domain.request.LoginRequest
import com.example.pulsesocial.domain.response.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val api: PulseSocialApi
) {

    suspend fun login(loginRequest: LoginRequest): LoginResponse {
        return api.login(loginRequest)
    }

}