package com.example.pulsesocial.feature.post

import android.net.Uri

object PostContract {

    data class State(
        val content: String = "",
        val imageUrl: String = "",
        val isLoading: Boolean = false,
    )

    sealed class Event {
        data class OnContentChange(val content: String): Event()
        data class OnImageUrlChange(val imageUrl: String): Event()
        data class OnPostClick(val imagemUri: Uri?) : Event()
    }

    sealed class Effect {
        data class ShowError(val errorMessage: String): Effect()
        data class ShowSuccess(val successMessage: String ): Effect()
    }

}