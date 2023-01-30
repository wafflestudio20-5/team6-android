package com.example.todomateclone.network.dto

data class CreateCommentRequest(
    val context: String,
)


data class GetCommentListResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<CommentDTO>
)