package com.example.todomateclone.network.dto

data class CreateTaskRequest(
    val name: String,
)

data class CheckTaskRequest(
    val uid: String,
)

//data class CreateTaskResponse(
//    val result: TaskDTO,
//)
//TaskDTO 자체를 Response로 사용