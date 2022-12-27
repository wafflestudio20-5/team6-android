package com.example.todomateclone.network

import com.example.todomateclone.network.dto.*
import retrofit2.Response
import retrofit2.http.*

interface RestService {
    @POST("/account/login")
    suspend fun login(@Body() request: LoginRequest): LoginResult

    @POST("/account/logout")
    suspend fun logout()

    @POST("/accounts/registration")
    suspend fun signup()

    // Simple CMS 과제에서 사용되었던 API 입니다.
//    @GET("/post/{postId}")
//    suspend fun getPost(@Path("postId") postId: Int): GetPostResponse
//
//    @GET("/post")
//    suspend fun getAllPostPaged(
//        @Query("cursor") cursor: Int? = null,
//        @Query("count") count: Int = 10
//    ): GetAllPostResponse
//
//    @POST("/post")
//    suspend fun createPost(@Body() request: CreatePostRequest): CreatePostResponse
//
//    @POST("/post/{postId}/comment")
//    suspend fun createComment(
//        @Body() request: CreateCommentRequest,
//        @Path("postId") postId: Int
//    ): CreateCommentResponse
//
//    // update on deletion
//    @DELETE("/post/{postId}")
//    suspend fun deletePost(@Path("postId") postId: Int): Response<Unit>
//
//    @DELETE("/post/comment/{commentId}")
//    suspend fun deleteComment(@Path("commentId") commentId: Int): Response<Unit>
}