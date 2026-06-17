package com.example.pulsesocial.feature.signup

import android.net.Uri

object SignUpContract {

    data class State (
        val username: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val imageUrl: Uri? = null,
        val isLoading: Boolean = false
    )

    sealed class Event {
        data class OnUsernameChange(val username: String): Event()
        data class OnEmailChange(val email: String): Event()
        data class OnPasswordChange(val password: String): Event()
        data class OnConfirmPasswordChange(val confirmPass: String): Event()
        data class OnImageChange(val image: Uri?): Event()
        object OnCreateClick: Event()
    }

    sealed class Effect {

        data class OnSignUpSuccess(val message: String): Effect()
        data class OnSignUpFailure(val message: String): Effect()
    }

}