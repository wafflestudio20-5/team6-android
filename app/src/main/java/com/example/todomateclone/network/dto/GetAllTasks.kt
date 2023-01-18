package com.example.todomateclone.network.dto

data class GetAllTaskResponse(
    val posts: List<TaskDTO>,
    val nextCursor: Int?
)
