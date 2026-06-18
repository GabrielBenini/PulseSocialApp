package com.example.pulsesocial.feature.feed

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
                isLoading = true,
            )

            try {

                val userId = sessionManager.getUserId() ?: return@launch
                val posts = repository.getPosts(userId)

                _uiState.value = _uiState.value.copy(
                    posts = posts,
                    currentUserId = userId,
                    isLoading = false,
                )

                _uiEffect.emit(
                    FeedContract.Effect.ShowSuccess(message = "Sucesso ao Carregar Posts")
                )

            } catch (e: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
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

                val userId = sessionManager.getUserId() ?: return@launch

                repository.deletePost(postId, userId)

                val updatedPosts = _uiState.value.posts.filter { it.id != postId }

                _uiState.value = _uiState.value.copy(
                    posts = updatedPosts
                )

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

            val userId = sessionManager.getUserId() ?: return@launch

            // 1. Guarda a lista original (se a internet cair, a gente volta pra ela)
            val originalPosts = _uiState.value.posts
            val post = originalPosts.find { it.id == postId } ?: return@launch

            // 2. MUDA A UI IMEDIATAMENTE ( antes de chamar nossa api )
            val updatedPosts = originalPosts.map {
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

            // 3. chama a api
            try {
                if (post.likedByUser) {
                    repository.unlikePost(postId, userId)
                } else {
                    repository.likePost(postId, userId)
                }
            } catch (e: Exception) {
                // 4. se der erro vamos desfazer a animacao do like
                _uiState.value = _uiState.value.copy(
                    posts = originalPosts
                )

                _uiEffect.emit(
                    FeedContract.Effect.ShowError("Erro ao curtir o post")
                )
            }
        }
    }
}
