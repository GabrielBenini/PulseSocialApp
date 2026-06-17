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

                val error = when {
                    event.username.isBlank() -> "Nome é obrigatório"
                    event.username.length < 3 -> "Nome deve ter pelo menos 3 caracteres"
                    else -> null
                }

                _uiState.value = _uiState.value.copy(
                    username = event.username,
                    usernameError = error
                )
            }

            is SignUpContract.Event.OnEmailChange -> {

                val error = when {
                    event.email.isBlank() -> "Email é obrigatório"
                    !android.util.Patterns.EMAIL_ADDRESS.matcher(event.email).matches() -> "Email inválido"
                    else -> null
                }

                _uiState.value = _uiState.value.copy(
                    email = event.email,
                    emailError = error
                )
            }

            is SignUpContract.Event.OnPasswordChange -> {

                val error = when {
                    event.password.isBlank() -> "Senha é obrigatória"
                    event.password.length < 8 -> "Senha precisa ter no mínimo 8 caracteres"
                    !event.password.any{ it.isDigit() } -> "Senha precisa ter um numero"
                    !event.password.any{ it.isUpperCase() } -> "Senha precisa ter uma letra maiuscula"
                    else -> null

                }

                _uiState.value = _uiState.value.copy(
                    password = event.password,
                    passwordError = error
                )
            }

            is SignUpContract.Event.OnConfirmPasswordChange -> {

                val error = when {
                    event.confirmPass != _uiState.value.password -> "As senhas devem ser identicas"
                    else -> null
                }

                _uiState.value = _uiState.value.copy(
                    confirmPassword = event.confirmPass,
                    confirmPasswordError = error
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