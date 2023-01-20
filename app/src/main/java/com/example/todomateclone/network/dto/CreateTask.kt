package com.example.todomateclone.network.dto

data class CreateTaskRequest(
    val name: String,
)

data class CreateTaskResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: TaskDTO,
)
