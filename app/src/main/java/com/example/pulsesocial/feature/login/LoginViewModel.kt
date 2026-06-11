package com.example.pulsesocial.feature.login

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pulsesocial.data.datastore.UserPreferencesRepository
import com.example.pulsesocial.data.repository.LoginRepository
import com.example.pulsesocial.domain.request.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginContract.State())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<LoginContract.Effect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: LoginContract.Event) {

        when(event){

            is LoginContract.Event.OnEmailChange -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email
                )
            }

            is LoginContract.Event.OnPassChange -> {
                _uiState.value = _uiState.value.copy(
                    password = event.password
                )
            }

            is LoginContract.Event.OnLoginClick -> {
                login()
            }
        }
    }


    private fun login(){

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {

                val response = repository.login(
                    loginRequest = LoginRequest(
                        email = _uiState.value.email,
                        password = _uiState.value.password
                    )
                )

                userPreferencesRepository.saveUser(
                    userId = response.id,
                    email = response.email
                )


                _uiEffect.emit(
                    LoginContract.Effect.OnLoginSuccess("Sucesso ao Logar")
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                Log.e("POST_ERROR", e.message.toString())

                _uiEffect.emit(
                    LoginContract.Effect.OnLoginFailure("Falha ao Logar")
                )
            }
        }
    }

}