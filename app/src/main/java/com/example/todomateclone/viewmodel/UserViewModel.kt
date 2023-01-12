package com.example.todomateclone.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.todomateclone.MainApplication
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.LoginRequest
import com.example.todomateclone.network.dto.ResendEmailRequest
import com.example.todomateclone.network.dto.SignupRequest
import com.example.todomateclone.network.dto.UserDTO
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster
import com.kakao.sdk.user.UserApiClient

class UserViewModel(
    private val restService: RestService,
    private val authStorage: AuthStorage,
    private val toaster: Toaster,
) : ViewModel() {

    suspend fun login(email: String, password: String) {
        try {
            val response = restService.login(LoginRequest(email, password))
            authStorage.setAuthInfo(
                response.access_token,
                response.refresh_token,
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

    // 카카오계정으로 로그인
    suspend fun kakaoLogin(accessToken: String) {
        try {
            restService.kakaoLogin(accessToken)
        } catch(e: Exception) {
            toaster.toastApiError(e)
        }
    }

}