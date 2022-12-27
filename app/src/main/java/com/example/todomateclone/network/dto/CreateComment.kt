package com.example.todomateclone.network.dto

data class CreateCommentRequest(
    val message: String,
)

data class CreateCommentResponse(
    val result: PostDTO
)