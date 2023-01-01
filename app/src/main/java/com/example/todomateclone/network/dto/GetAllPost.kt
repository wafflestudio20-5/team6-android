package com.example.todomateclone.network.dto

data class GetAllPostResponse(
    val posts: List<PostDTO>,
    val nextCursor: Int?
)
