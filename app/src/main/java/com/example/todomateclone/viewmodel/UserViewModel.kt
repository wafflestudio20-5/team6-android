package com.example.todomateclone.viewmodel


import androidx.lifecycle.ViewModel
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.*
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster


class UserViewModel(
    private val restService: RestService,
    private val authStorage: AuthStorage,
    private val toaster: Toaster,
) : ViewModel() {

    suspend fun login(email: String, password: String) {
        try {
            val response = restService.login(LoginRequest(email, password))
            authStorage.setAuthInfo(
                response.accessToken,
                response.refreshToken,
                UserDTO(response.user.id, response.user.email, response.user.nickname, response.user.detail))
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }
    suspend fun resendEmail(email: String) {
        try {
            restService.resendEmail(ResendEmailRequest(email))
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun signup(email: String, password1: String, password2: String){
        try {
            restService.signup(SignupRequest(email, password1, password2))
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun logout(){
        try {
            restService.logout(accessToken = authStorage.authInfo.value?.accessToken.toString())
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun deleteUser(){
        try {
            restService.deleteUser(accessToken = authStorage.authInfo.value?.accessToken.toString(),
                id = authStorage.authInfo.value?.user!!.id)
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }
}