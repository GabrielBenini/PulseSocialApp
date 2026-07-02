package com.example.pulsesocial.feature.settings

object SettingsContract {

    sealed class Event {
        object OnLogoutClick: Event()
    }

    sealed class Effect {
        object NavigateToLogin: Effect()
    }

}