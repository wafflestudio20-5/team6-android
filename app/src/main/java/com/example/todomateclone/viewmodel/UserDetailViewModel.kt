package com.example.todomateclone.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.GetUserRequest
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
    // 처음 UserDTO는 그럼 어디서 등록??
    private val _user = MutableStateFlow<UserDTO?>(null)
    val user: StateFlow<UserDTO?> = _user
    val isOwner: StateFlow<Boolean> =
        _user.combine(authStorage.authInfo.map { it?.user?.id }) { user, userId ->
            userId != null && user?.id == userId
        }.stateIn(viewModelScope, started = SharingStarted.WhileSubscribed(), initialValue = false)

    suspend fun getUser() {
        try {
            _user.value = restService.getUser(
                accessToken = authStorage.authInfo.value?.accessToken.toString(),
                GetUserRequest()
            ).userDTO

        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun updateUser(userDTO: UserDTO){
        try {
            _user.value = restService.updateUser(accessToken = authStorage.authInfo.value?.accessToken.toString(),
                UpdateUserRequest(userDTO)
            ).userDTO
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

}