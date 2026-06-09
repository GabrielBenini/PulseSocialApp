package com.example.pulsesocial.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val context: Context
) {

    val userId: Flow<Long?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.USER_ID]
        }

    val email: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.EMAIL]
        }



    suspend fun saveUser(
        userId: Long,
        email: String,
    ) {

        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
            preferences[PreferencesKeys.EMAIL] = email
        }
    }

    suspend fun clearSession(){
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}