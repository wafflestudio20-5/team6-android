package com.example.todomateclone.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todomateclone.network.RestService

import com.example.todomateclone.network.dto.SignupRequest
import com.example.todomateclone.network.dto.UpdateUserRequest
import com.example.todomateclone.network.dto.UserDTO
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster
import kotlinx.coroutines.flow.*


class UserDetailViewModel (
    private val restService: RestService,
    private val authStorage: AuthStorage,
    private val toaster: Toaster,
) : ViewModel() {

    private val _user = MutableStateFlow<UserDTO?>(null)
    val user: StateFlow<UserDTO?> = _user

    suspend fun getUser() {
        try {
            _user.value = restService.getUser().user

        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun updateUser(userDTO: UserDTO){
        try {
            _user.value = restService.updateUser(
                UpdateUserRequest(userDTO)
            ).user
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

}