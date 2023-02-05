package com.example.todomateclone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.*
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster
import kotlinx.coroutines.flow.*


class UserDetailViewModel (
    private val restService: RestService,
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

    suspend fun sendResetEmail(email: String){
        try {
            restService.sendResetEmail(SendResetEmailRequest(email))
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun confirmPasswordChangeRequest(code : String, email: String,
                                             new_password1 : String,new_password2 : String){
        try {
            restService.confirmPasswordChange(ConfirmPasswordChangeRequest(code,email,new_password1,new_password2))
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }
}
