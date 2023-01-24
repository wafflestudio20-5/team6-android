package com.example.todomateclone.network.dto

data class GetTasksByDateResponse(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<TaskDTO>
)