package com.example.todomateclone.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.*
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster
import com.kakao.usermgmt.StringSet.email
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val restService: RestService,
    private val authStorage: AuthStorage,
    private val toaster: Toaster,
) : ViewModel() {

    private val _searcheduser = MutableStateFlow<UserDTO?>(null)
    val searcheduser: StateFlow<UserDTO?> = _searcheduser

    private val _isfollowing = MutableStateFlow<CheckFollowResponse?>(null)
    val isfollowing: StateFlow<CheckFollowResponse?> = _isfollowing


    suspend fun login(email: String, password: String) {
        try {
            val response = restService.login(LoginRequest(email, password))
            authStorage.setAuthInfo(
                response.access_token,
                response.refresh_token,
                AuthStorageUserDTO(response.user.id, response.user.email)
            )
            Log.d("UserViewModel", "authInfo is valid")
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

    suspend fun confirmCode(email: String, code: String) {
        try {
            restService.signupConfirm(SignUpConfirmRequest(email, code))
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

    suspend fun deleteUser() {
        try {
            restService.deleteUser(
                id = authStorage.authInfo.value?.user!!.id
            )
        } catch (e: Exception) {
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

    //search user

    suspend fun searchUser(email: String) {
        viewModelScope.launch {
            try {
                _searcheduser.value = restService.searchUser(
                    email = email
                )
            } catch (e: JsonDataException) {
                _searcheduser.value=null
            }
            catch (e: Exception) {
                _searcheduser.value=null
                toaster.toastApiError(e)
            }
        }
    }

    //follow user
    suspend fun followUser(followee: Int) {
        viewModelScope.launch {
            try {
                restService.followUser(
                    FollowRequest(followee = followee)
                )
            } catch (e: Exception) {
                toaster.toastApiError(e)
            }
        }
    }

    suspend fun checkFollow(uid: Int) {
        viewModelScope.launch {
            try {
                _isfollowing.value = restService.checkFollow(uid = uid)
            } catch (e: Exception) {
                toaster.toastApiError(e)
            }
        }
    }





}