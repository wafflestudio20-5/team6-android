package com.example.todomateclone.network.dto

data class SignupRequest(
    val email: String,
    val password1: String,
    val password2: String,
)

data class SignupResult(
    val detail: String
)

data class SignUpConfirmRequest(
    val email: String,
    val code: String
)

data class SignUpConfirmResult(
    val message: String
)

data class ResendEmailRequest(
    val email: String,
)

data class ResendEmailResult(
    val detail: String
)
