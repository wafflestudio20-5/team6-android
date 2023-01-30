package com.example.todomateclone.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.CreateDiaryRequest
import com.example.todomateclone.network.dto.DiaryDTO
import com.example.todomateclone.network.dto.UpdateDiaryRequest
import com.example.todomateclone.util.AuthStorage
import com.example.todomateclone.util.Toaster
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DiaryViewModel(
    private val restService: RestService,
    private val toaster: Toaster,
): ViewModel() {

    private val _diaryList = MutableStateFlow<List<DiaryDTO>?>(null)
    val diaryList: StateFlow<List<DiaryDTO>?> = _diaryList
    private val _diary = MutableStateFlow<DiaryDTO?>(null)
    val diary: StateFlow<DiaryDTO?> = _diary

    suspend fun getDiaryList() {
        try {
            val response = restService.getDiaryList()
            _diaryList.value = response.results
            Log.d("Diary", "get diary list successfully")
        } catch (e: Exception) {
            toaster.toastApiError(e)
            Log.d("Diary", "failed to get diary list")
        }
    }

    suspend fun getDateDiaryList() {
        try {
            val response = restService.getDateDiary(date = "2023-01-01")
            _diaryList.value = response.results
            Log.d("Diary", "get date_diary list successfully")
        } catch (e: Exception) {
            toaster.toastApiError(e)
            Log.d("Diary", "failed to get date_diary list")
        }
    }

    suspend fun createDiary(title: String, content: String) {
        try {
            restService.createDateDiary(CreateDiaryRequest(title = title, context = content), "2023-01-01")
            Log.d("Diary", "create new diary successfully")
        } catch (e: Exception) {
            toaster.toastApiError(e)
            Log.d("Diary", "failed to create diary")
        }
    }

    suspend fun getIdDiary(diaryId: Int) {
        try {
            _diary.value = restService.getIdDiary(did = diaryId)
        } catch (e: Exception) {
            toaster.toastApiError(e)
            Log.d("Diary", "failed to get diary")
        }
    }

    suspend fun updateIdDiary(title: String, content: String, diaryId: Int) {
        try {
            restService.updateIdDiary(UpdateDiaryRequest(content, title), did = diaryId)
        } catch (e: Exception) {
            toaster.toastApiError(e)
            Log.d("Diary", "failed to get diary")
        }
    }

    suspend fun deleteIdDiary(diaryId: Int) {
        try {
            restService.deleteIdDiary(did = diaryId)
        } catch (e: Exception) {
            toaster.toastApiError(e)
            Log.d("Diary", "failed to get diary")
        }
    }
}