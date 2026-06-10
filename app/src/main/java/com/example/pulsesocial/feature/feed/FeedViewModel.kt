package com.example.pulsesocial.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pulsesocial.data.datastore.UserPreferencesRepository
import com.example.pulsesocial.data.repository.PostRepository
import com.example.pulsesocial.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: PostRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FeedContract.State())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<FeedContract.Effect>()
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleEvent(event: FeedContract.Event) {

        when (event) {

            FeedContract.Event.LoadPosts -> {
                loadPosts()
            }

            is FeedContract.Event.OnLikeClick -> {
                likePost(event.postId)
            }


            is FeedContract.Event.OnRefresh -> {
                loadPosts()
            }

            is FeedContract.Event.OnDeletePostClick -> {
                deletePost(event.postId)
            }

        }
    }

    private fun loadPosts() {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true
            )

            try {

                val userId = sessionManager.getUserId() ?: return@launch
                val posts = repository.getPosts(userId)

                _uiState.value = _uiState.value.copy(
                    posts = posts,
                    isLoading = false
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false
                )

                _uiEffect.emit(
                    FeedContract.Effect.ShowError("Erro ao carregar posts")
                )
            }
        }
    }

    private fun deletePost(postId: Long){

        viewModelScope.launch {

            try {
                repository.deletePost(postId)

                _uiEffect.emit(
                    FeedContract.Effect.ShowSuccess("Post excluido com successo.")
                )

            } catch (e: Exception){

                _uiEffect.emit(
                    FeedContract.Effect.ShowError("Erro ao excluir post")
                )
            }
        }
    }

    private fun likePost(postId: Long) {

        viewModelScope.launch {

            try {

                val userId = sessionManager.getUserId() ?: return@launch

                val post = _uiState.value.posts.find { it.id == postId } ?: return@launch

                if (post.likedByUser) {
                    repository.unlikePost(postId, userId)
                } else {
                    repository.likePost(postId, userId)
                }

                val updatedPosts = _uiState.value.posts.map {

                    if (it.id == postId) {
                        it.copy(
                            likesCount = if (it.likedByUser) it.likesCount - 1 else it.likesCount + 1,
                            likedByUser = !it.likedByUser
                        )
                    } else it
                }

                _uiState.value = _uiState.value.copy(
                    posts = updatedPosts
                )

            } catch (e: Exception) {

                _uiEffect.emit(
                    FeedContract.Effect.ShowError("Erro ao curtir o post")
                )
            }
        }
    }

}
