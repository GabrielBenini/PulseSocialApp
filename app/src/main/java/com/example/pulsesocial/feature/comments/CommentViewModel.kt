package com.example.pulsesocial.feature.comments

import androidx.compose.ui.window.isPopupLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pulsesocial.data.repository.PostRepository
import com.example.pulsesocial.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
    private val repository: PostRepository,
    private val sessionManager: SessionManager
): ViewModel() {

    private val _uiState = MutableStateFlow(CommentContract.State())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<CommentContract.Effect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: CommentContract.Event) {
        when(event) {
            is CommentContract.Event.LoadComments -> {
                loadComments(event.postId)
            }

            is CommentContract.Event.SendComment -> {
                sendComment(event.postId, event.content)
            }
        }
    }

    private fun loadComments(postId: Long) {

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val comments = repository.getComments(postId)
                _uiState.value = _uiState.value.copy(
                    comments = comments,
                    isLoading = false
                )            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                _uiEffect.emit(CommentContract.Effect.ShowError("Erro ao carregar comentarios"))
            }
        }
    }

    private fun sendComment(postId: Long, content: String) {

        viewModelScope.launch {
            try {
                val userId = sessionManager.getUserId() ?: return@launch
                repository.createComment(postId, userId, content)
                loadComments(postId)
                loadComments(postId)

            } catch (e: Exception){
                _uiEffect.emit(CommentContract.Effect.ShowError("Erro ao enviar comentario"))
            }
        }
    }
}