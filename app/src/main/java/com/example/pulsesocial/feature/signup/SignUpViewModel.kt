package com.example.pulsesocial.feature.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pulsesocial.data.repository.FirebaseStorageRepository
import com.example.pulsesocial.data.repository.SignUpRepository
import com.example.pulsesocial.domain.request.UserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: SignUpRepository,
    private val storageRepository: FirebaseStorageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpContract.State())
    val uiState = _uiState.asStateFlow()

    var imageUrl: String = ""

    private val _uiEffect = MutableSharedFlow<SignUpContract.Effect>(
        extraBufferCapacity = 1
    )
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: SignUpContract.Event) {
        when (event) {

            is SignUpContract.Event.OnUsernameChange -> {
                _uiState.value = _uiState.value.copy(
                    username = event.username
                )
            }

            is SignUpContract.Event.OnEmailChange -> {
                _uiState.value = _uiState.value.copy(
                    email = event.email
                )
            }

            is SignUpContract.Event.OnPasswordChange -> {
                _uiState.value = _uiState.value.copy(
                    password = event.password
                )
            }

            is SignUpContract.Event.OnConfirmPasswordChange -> {
                _uiState.value = _uiState.value.copy(
                    confirmPassword = event.confirmPass
                )
            }

            is SignUpContract.Event.OnImageChange -> {
                _uiState.value = _uiState.value.copy(
                    imageUrl = event.image
                )
            }

            is SignUpContract.Event.OnCreateClick -> {
                createAccount()
            }
        }

    }

    private fun createAccount() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {
                imageUrl = _uiState.value.imageUrl?.let {
                    storageRepository.uploadProfileImage(it)
                } ?: ""
            } catch (e: Exception) {
                _uiEffect.emit(SignUpContract.Effect.OnSignUpFailure("Erro na imagem"))
                return@launch
            }

            try {

                val response = repository.createUser(
                    userRequest = UserRequest(
                        username = _uiState.value.username,
                        email = _uiState.value.email,
                        password = _uiState.value.password,
                        imageUrl = imageUrl
                    )
                )

                if (response.isSuccessful && response.body() != null) {
                    _uiEffect.emit(SignUpContract.Effect.OnSignUpSuccess("Usuario Cadastrado"))
                }

            } catch (e: Exception) {
                Log.e("SIGNUP_ERROR", "Erro ao criar usuario", e)
                _uiEffect.emit(
                    SignUpContract.Effect.OnSignUpFailure("Falha ao cadastrar usuario")
                )
            } finally {
                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }

        }
    }

}