package com.example.pulsesocial.data.api

import com.example.pulsesocial.domain.request.LoginRequest
import com.example.pulsesocial.domain.request.PostRequest
import com.example.pulsesocial.domain.response.LikeResponse
import com.example.pulsesocial.domain.response.LoginResponse
import com.example.pulsesocial.domain.response.PostResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PulseSocialApi {

    @GET("posts")
    suspend fun getPosts(
        @Query("userId") userId: Long
    ): List<PostResponse>

    @POST("posts")
    suspend fun createPost(
        @Body request: PostRequest
    ): PostResponse

    @POST("posts/{postId}/like")
    suspend fun likePost(
        @Path("postId") postId: Long,
        @Query("userId") userId: Long
    ): LikeResponse

    @DELETE("posts/{postId}/unlike")
    suspend fun unlikePost(
        @Path("postId") postId: Long,
        @Query("userId") userId: Long
    )

    @POST("users/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}