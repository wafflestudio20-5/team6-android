package com.example.todomateclone.util

import android.content.Context
import androidx.core.content.edit
import com.example.todomateclone.network.dto.UserDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthStorage(
    context: Context
) {
    // Shared preference stores data(key-value) on the app folder. When user removes the app the data is also removed.
    private val sharedPref =
        context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE)
    private val _authInfo: MutableStateFlow<AuthInfo?> =
        MutableStateFlow(
            if ((sharedPref.getString(AccessTokenKey, "") ?: "").isEmpty()) {
                null
            } else {
                AuthInfo(
                    accessToken = sharedPref.getString(AccessTokenKey, "")!!,
//                    refreshToken = sharedPref.getString(RefreshTokenKey, "")!!,
                    UserDTO(
                        id = sharedPref.getInt(UserIdKey, -1),
                        email = sharedPref.getString(EmailKey, "")!!,
                        nickname = sharedPref.getString(NicknameKey, "")!!,
                        detail = sharedPref.getString(DetailKey, "")!!,
                    )
                )
            }
        )
    val authInfo: StateFlow<AuthInfo?> = _authInfo

    fun setAuthInfo(accessToken: String, refreshToken: String, user: UserDTO) {
        _authInfo.value = AuthInfo(accessToken, user)
        sharedPref.edit {
            putString(AccessTokenKey, accessToken)
            putString(RefreshTokenKey, refreshToken)
            putInt(UserIdKey, user.id)
            putString(EmailKey, user.email)
            putString(NicknameKey, user.nickname)
            putString(DetailKey, user.detail)
        }
    }

    data class AuthInfo(
        val accessToken: String,
//        val refreshToken: String,
        val user: UserDTO,
    )

    companion object {
        const val AccessTokenKey = "access_token"
        const val RefreshTokenKey = "refresh_token"
        const val EmailKey = "email"
        const val UserIdKey = "user_id"
        const val NicknameKey = "nickname"
        const val DetailKey = "detail"
        const val SharedPreferenceName = "auth_pref"
    }
}