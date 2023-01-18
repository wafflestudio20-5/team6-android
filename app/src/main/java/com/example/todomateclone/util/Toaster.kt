package com.example.todomateclone.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.todomateclone.network.dto.ErrorDTO
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException


class Toaster(
    private val context: Context,
    private val moshi: Moshi,
) {
    @Suppress("OPT_IN_USAGE")
    fun toast(message: String) {
        // 귀찮아서...
        GlobalScope.launch(Dispatchers.Main) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun toastApiError(throwable: Throwable) {
        val message = when (throwable) {
            is HttpException -> throwable.parseError()?.message
            is IOException -> "Internet connection is unstable."
            else -> null
        } ?: "Unknown error occurred."
        Log.e("Toaster", "toast api error", throwable)
        toast(message)
    }

    private fun HttpException.parseError(): ErrorDTO? {
        val rawStr = this.response()?.errorBody()?.string()
        return try {
            rawStr?.let { moshi.adapter(ErrorDTO::class.java).fromJson(it) }
        } catch (e: Exception) {
            null
        }
    }
}