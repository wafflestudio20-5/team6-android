package com.example.todomateclone.network.dto

data class FollowRequest(
    val followee: Int,
)


data class FollowResponse(
    val detail: String,
)

data class CheckFollowResponse(
    val is_following: Boolean,
)

data class GetFolloweeListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<FolloweeDTO>
)

data class GetFollowerListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<FollowerDTO>
)

//data class CreateTaskResponse(
//    val result: TaskDTO,
//)
//TaskDTO 자체를 Response로 사용