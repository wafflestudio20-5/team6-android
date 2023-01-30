package com.example.todomateclone.network.dto

data class CreateTaskRequest(
    val name: String,
    val start_time: String,
    val end_time: String
)


data class ChangeTaskRequest(
    val name: String,
    val date: String,
    val start_time: String,
    val end_time: String
)

//data class CreateTaskResponse(
//    val result: TaskDTO,
//)
//TaskDTO 자체를 Response로 사용