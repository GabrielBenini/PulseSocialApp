package com.example.pulsesocial.feature.login

import android.os.Message

object LoginContract {

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false
    )

    sealed class Event{

        data class OnEmailChange(val email: String): Event()
        data class OnPassChange(val password: String): Event()
        object OnLoginClick: Event()
    }

    sealed class Effect{

        data class OnLoginSuccess(val successMessage: String): Effect()
        data class OnLoginFailure(val failureMessage: String): Effect()
    }

}