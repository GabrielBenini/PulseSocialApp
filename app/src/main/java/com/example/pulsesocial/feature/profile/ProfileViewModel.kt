package com.example.pulsesocial.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pulsesocial.data.repository.LoginRepository
import com.example.pulsesocial.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
): ViewModel() {

    private val _uiEffect = MutableSharedFlow<ProfileContract.Effect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: ProfileContract.Event) {

        when(event) {

            is ProfileContract.Event.OnLogoutClick -> {

            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionManager.logout()
            _uiEffect.emit(ProfileContract.Effect.NavigateToLogin)
        }
    }
}

