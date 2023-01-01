package com.example.todomateclone.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.LoginRequest
import com.example.todomateclone.network.dto.UserDTO
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster

class UserViewModel(
    private val restService: RestService,
    private val authStorage: AuthStorage,
    private val toaster: Toaster,
) : ViewModel() {

    suspend fun login(username: String, password: String) {
        try {
            val response = restService.login(LoginRequest(username, password))
            authStorage.setAuthInfo(response.accessToken, UserDTO(1, "temp"))
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }
}

//class UserViewModelFactory(
//    private val restService: RestService,
//    private val authStorage: AuthStorage,
//    private val toaster: Toaster,
//): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return UserViewModel(restService, authStorage, toaster) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}