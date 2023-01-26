package com.example.todomateclone.network

import com.example.todomateclone.network.dto.*
import retrofit2.Response
import retrofit2.http.*
import java.time.LocalDate

interface RestService {
    @POST("/accounts/login/")
    suspend fun login(@Body() request: LoginRequest): LoginResult

    @POST("/accounts/registration/")
    suspend fun signup(@Body() request: SignupRequest)

    @POST("/accounts/registration/resend-email/")
    suspend fun resendEmail(@Body() request: ResendEmailRequest): ResendEmailResult

    @POST("/accounts/kakao/login/")
    suspend fun kakaoLogin(@Body() request: SocialLoginRequest): LoginResult

    @POST("/accounts/google/login/")
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


    @POST("/task/list/{date}/")
    suspend fun createTask(@Body() request: CreateTaskRequest, @Path("date") date: String): TaskDTO

    @GET("/task/list/{date}/")
    suspend fun getTasksByDateFirstPage(@Path("date") date: String): GetTasksByDateResponse

    @GET("/task/list/{date}/")
    suspend fun getTasksByDatePaged(@Path("date") date: String, @Query("page") page: Int): GetTasksByDateResponse

    @GET("/task/list/")
    suspend fun getAllTasksPaged(): GetAllTasksResponse

    @GET("/task/detail/{tid}/check/")
    suspend fun checkTask(@Path("tid") tid: Int): TaskDTO

    @DELETE("/task/detail/{tid}/")
    suspend fun deleteTask(@Path("tid") tid: Int): TaskDTO?

    @GET("/task/detail/{tid}/delay")
    suspend fun delayTask(@Path("tid") tid: Int): TaskDTO?


//    @POST("/post/{postId}/comment")
//    suspend fun createComment(
//        @Body() request: CreateCommentRequest,
//        @Path("postId") postId: Int
//    ): CreateCommentResponse
//
    // update on deletion
//    @DELETE("/post/{postId}")
//    suspend fun deletePost(@Path("postId") postId: Int): Response<Unit>

//    @DELETE("/post/comment/{commentId}")
//    suspend fun deleteComment(@Path("commentId") commentId: Int): Response<Unit>
}