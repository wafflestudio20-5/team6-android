package com.example.todomateclone.network.dto

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResult(
    val access_token: String,
    val refresh_token: String,
    val user: UserDTO
)

data class SocialLoginRequest(
    val access_token: String,
    val code: String = ""
)