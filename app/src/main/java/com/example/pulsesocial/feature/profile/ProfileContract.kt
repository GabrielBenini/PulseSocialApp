package com.example.pulsesocial.feature.profile

object ProfileContract {

    sealed class Event {
        object OnLogoutClick: Event()
    }

    sealed class Effect {
        object NavigateToLogin: Effect()
        data class OnLogoutSuccess(val message: String): Effect()
        data class OnLogoutFailure(val message: String): Effect()
    }

}