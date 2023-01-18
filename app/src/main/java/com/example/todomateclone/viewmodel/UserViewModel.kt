package com.example.todomateclone.viewmodel


import android.util.Log
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
                response.access_token,
                response.refresh_token,
                AuthStorageUserDTO(response.user.id, response.user.email)
            )
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    // Apply() writes the changes to the in-memory SharedPreferences immediately but begins an asynchronous commit to disk
    fun logout() {
        authStorage.sharedPref.edit().clear().apply()
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

    // login with kakao account
    suspend fun kakaoLogin(accessToken: String) {
        try {
            Log.d("KakaoLogin", "send idToken to server")
            val response = restService.kakaoLogin(SocialLoginRequest(accessToken))
            authStorage.setAuthInfo(
                response.access_token,
                response.refresh_token,
                AuthStorageUserDTO(response.user.id, response.user.email)
            )
        } catch(e: Exception) {
            toaster.toastApiError(e)
        }
    }

    // login with google account
    suspend fun googleLogin(accessToken: String) {
        try {
            Log.d("GoogleLogin", "send idToken to server")
            val response = restService.googleLogin(SocialLoginRequest(accessToken))
            authStorage.setAuthInfo(
                response.access_token,
                response.refresh_token,
                AuthStorageUserDTO(response.user.id, response.user.email)
            )
        } catch(e: Exception) {
            toaster.toastApiError(e)
        }
    }
}