package com.example.todomateclone.network.dto

import java.time.LocalDateTime

data class UserDTO(
    val id: Int,
    val email: String,
    val nickname: String,
    val detail: String,
)

data class CommentDTO(
    val id: Int,
    val message: String,
    val author: UserDTO,
    val createdAt: LocalDateTime,
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