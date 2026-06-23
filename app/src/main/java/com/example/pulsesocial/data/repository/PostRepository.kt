package com.example.pulsesocial.data.repository

import com.example.pulsesocial.data.api.PulseSocialApi
import com.example.pulsesocial.domain.request.CommentRequest
import com.example.pulsesocial.domain.request.PostRequest
import com.example.pulsesocial.domain.response.CommentResponse
import com.example.pulsesocial.domain.response.LikeResponse
import com.example.pulsesocial.domain.response.PostResponse
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val api: PulseSocialApi
) {

    suspend fun getPosts(userId: Long): List<PostResponse>{
        return api.getPosts(userId)
    }

    suspend fun createPosts(request: PostRequest): PostResponse{
        return api.createPost(request)
    }

    suspend fun likePost(postId: Long, userId: Long): LikeResponse {
        return api.likePost(postId, userId)
    }

    suspend fun unlikePost(postId: Long, userId: Long) {
        return api.unlikePost(postId, userId)
    }

    suspend fun deletePost(postId: Long, userId: Long){
        return api.deletePost(postId, userId)
    }

    suspend fun getComments(postId: Long): List<CommentResponse> {
        return api.getComments(postId)
    }

    suspend fun createComment(postId: Long, userId: Long, content: String){
        val request = CommentRequest(content = content, postId = postId, userId = userId)
        api.createComment(postId, request)
    }


}