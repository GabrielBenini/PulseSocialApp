package com.example.pulsesocial.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


val Context.dataStore by preferencesDataStore(
    name = "user_preferences"
)

object PreferencesKeys {

    val USER_ID = longPreferencesKey("user_id")
    val EMAIL = stringPreferencesKey("email")
}