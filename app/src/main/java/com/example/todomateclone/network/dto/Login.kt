package com.example.todomateclone.network.dto

data class LoginRequest(
    val id: String,
    val password: String
)

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val user: UserDTO
)