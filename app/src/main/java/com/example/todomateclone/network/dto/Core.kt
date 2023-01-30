package com.example.todomateclone.network.dto

import android.media.Image
import retrofit2.http.Url
import java.net.URL
import java.time.LocalDate
import java.time.LocalDateTime

data class UserDTO(
    val id: Int,
    val email: String,
    val nickname: String,
    val detail: String,
//    val image: String
)

data class AuthStorageUserDTO(
    val id: Int,
    val email: String,
)

data class CommentDTO(
    val id: Int,
    val context: String,
    val diary: String,
    val created_at: String,
    val updated_at: String,
    val created_by: Int,
    val nickname: String
)

data class DiaryDTO(
    val id: Int,
    val date: String,
    val title: String,
    val context: String,
    val created_by: Int,
    val nickname: String
)

data class TaskDTO(
    val id: Int,
    val date: String,
    val name: String,
    val complete: Boolean,
    val created_by: Int,
)

data class PostDTO(
    val id: Int,
    val content: String,
    val title: String,
    val author: UserDTO,
    val comments: List<CommentDTO>,
    val createdAt: LocalDateTime,
)

data class ErrorDTO(
    val statusCode: Int?,
    val message: String?,
)