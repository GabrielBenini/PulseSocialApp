package com.example.pulsesocial.feature.post

import android.util.Log
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pulsesocial.data.datastore.UserPreferencesRepository
import com.example.pulsesocial.data.repository.PostRepository
import com.example.pulsesocial.data.session.SessionManager
import com.example.pulsesocial.domain.request.PostRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostContract.State())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<PostContract.Effect>(
        extraBufferCapacity = 1
    )

    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: PostContract.Event) {

        when (event) {

            is PostContract.Event.OnContentChange -> {
                _uiState.value = _uiState.value.copy(
                    content = event.content
                )
            }

            is PostContract.Event.OnImageUrlChange -> {
                _uiState.value = _uiState.value.copy(
                    imageUrl = event.imageUrl
                )
            }

            is PostContract.Event.OnPostClick -> {
                createPost()
            }

        }
    }

    private fun createPost(){

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {

                val userId = sessionManager.getUserId()

                repository.createPosts(
                    request = PostRequest(
                        content = _uiState.value.content,
                        imageUrl = _uiState.value.imageUrl,
                        userId = userId!!
                    )
                )

                Log.d("POST_DEBUG", "Post criado")

                _uiEffect.emit(
                    PostContract.Effect.ShowSuccess("Post criado com successo")
                )


                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

            } catch (e: Exception){

                Log.e("POST_ERROR", e.message.toString())

                _uiEffect.emit(
                    PostContract.Effect.ShowError("Erro ao criar post")
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )
            }
        }
    }

}

