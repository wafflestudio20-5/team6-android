package com.example.todomateclone.network

import com.example.todomateclone.network.dto.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface RestService {
    @POST("/accounts/login/")
    suspend fun login(@Body() request: LoginRequest): LoginResult

    @POST("/accounts/logout")
    suspend fun logout(
        @Header("authorization") accessToken: String,
    ): Response<Unit>

    @POST("/accounts/registration/")
    suspend fun signup(@Body() request: SignupRequest): Response<Unit>

    @POST("/accounts/registration/resend-email")
    suspend fun resendEmail(@Body() request: ResendEmailRequest): ResendEmailResult

    @GET("/accounts/user/")
    suspend fun getUser(): GetUserResult

    @PUT("/accounts/user/")
    suspend fun updateUser(
        @Body() request: UpdateUserRequest
    ): UpdateUserResult

    @DELETE("/accounts/user/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int
    ): Response<Unit>

    @POST("/accounts/kakao/login")
    suspend fun kakaoLogin(@Body() request: SocialLoginRequest): LoginResult

    @POST("/accounts/google/login")
    suspend fun googleLogin(@Body() request: SocialLoginRequest): LoginResult


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