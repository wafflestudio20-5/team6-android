package com.example.todomateclone.viewmodel

import androidx.lifecycle.ViewModel
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.CommentDTO
import com.example.todomateclone.network.dto.CreateCommentRequest
import com.example.todomateclone.network.dto.UpdateCommentRequest
import com.example.todomateclone.util.Toaster
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CommentViewModel(
    private val restService: RestService,
    private val toaster: Toaster,
): ViewModel() {
    private val _commentList = MutableStateFlow<List<CommentDTO>?>(null)
    val commentList: StateFlow<List<CommentDTO>?> = _commentList
    private val _comment = MutableStateFlow<CommentDTO?>(null)
    val comment: StateFlow<CommentDTO?> = _comment

    suspend fun getCommentList(did: Int) {
        try {
            _commentList.value =  restService.getCommentList(did).results
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun createComment(content: String, did: Int) {
        try {
            restService.createComment(CreateCommentRequest(content), did)
            toaster.toast("댓글이 생성되었습니다.")
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun getIdComment(cid: Int) {
        try {
            _comment.value = restService.getIdComment(cid)
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun updateIdComment(content: String, cid: Int) {
        try {
            restService.updateIdComment(UpdateCommentRequest(content), cid)
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }

    suspend fun deleteIdComment(cid: Int) {
        try {
            restService.deleteIdComment(cid)
        } catch (e: Exception) {
            toaster.toastApiError(e)
        }
    }
}