package com.example.todomateclone.network.dto

data class SendResetEmailRequest(
    val email : String
)

data class ConfirmPasswordChangeRequest(
    val code : String,
    val new_password1 : String,
    val new_password2 : String
)
