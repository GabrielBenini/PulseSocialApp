package com.example.pulsesocial.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pulsesocial.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {

    private val _uiEffect = MutableSharedFlow<SettingsContract.Effect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: SettingsContract.Event){

        when(event) {
            is SettingsContract.Event.OnLogoutClick -> {
                logout()
            }
        }

    }

    fun logout(){
        viewModelScope.launch {
            sessionManager.logout()
            _uiEffect.emit(SettingsContract.Effect.NavigateToLogin)
        }
    }
}