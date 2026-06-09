package com.example.pulsesocial.data.session

import com.example.pulsesocial.data.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend fun getUserId(): Long? {
        return userPreferencesRepository.userId.first()
    }

    suspend fun getUserEmail(): String? {
        return userPreferencesRepository.email.first()
    }

    suspend fun logout() {
        return userPreferencesRepository.clearSession()
    }
}