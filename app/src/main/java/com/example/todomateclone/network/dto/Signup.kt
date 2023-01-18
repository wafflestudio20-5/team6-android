package com.example.todomateclone.network.dto

data class SignupRequest(
    val email: String,
    val password1: String,
    val password2: String,
)

//data class SignupResult(
//    val response: Boolean = true
//)

data class ResendEmailRequest(
    val email: String,
)

data class ResendEmailResult(
    val detail: String = "ok"
)
