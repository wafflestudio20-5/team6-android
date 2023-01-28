package com.example.todomateclone.network.dto

data class GetDiaryListResponse(
    val count: Int = 1,
    val next: String? = null,
    val previous: String? = null,
    val results: List<DiaryDTO>
)

data class GetDateDiaryResponse(
    val count: Int = 1,
    val next: String? = null,
    val previous: String? = null,
    val results: List<DiaryDTO>
)