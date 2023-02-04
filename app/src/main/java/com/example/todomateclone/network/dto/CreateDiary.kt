package com.example.todomateclone.network.dto

data class CreateDiaryRequest(
    val context: String,
    val title: String
)

data class CreateDiaryResponse(
    val count: Int = 1,
    val next: String? = null,
    val previous: String? = null,
    val results: List<DiaryDTO>
)