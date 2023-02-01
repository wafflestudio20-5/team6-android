package com.example.todomateclone.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.ChangeTaskRequest
import com.example.todomateclone.network.dto.CreateTaskRequest
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.todo.SearchedTaskPagingSource
import com.example.todomateclone.ui.todo.TaskPagingSource
import com.example.todomateclone.util.Toaster
import com.kakao.usermgmt.StringSet
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException


class TodoViewModel(
    private val restService: RestService,
    private val toaster: Toaster
) : ViewModel() {
    val job = Job()

    fun createPager(date: String): Flow<PagingData<TaskDTO>> {
        return Pager(PagingConfig(pageSize = 10)) {
            TaskPagingSource(restService, date)
        }.flow.cachedIn(viewModelScope)
    }

    fun createSearchedPager(date: String, uid: Int): Flow<PagingData<TaskDTO>> {
        return Pager(PagingConfig(pageSize = 10)) {
            SearchedTaskPagingSource(restService, date, uid)
        }.flow.cachedIn(viewModelScope)
    }


    suspend fun createTodo(name: String, date: String, start_time: String, end_time: String) {
        viewModelScope.launch(job) {
            try {
                if (name == "") toaster.toast("이름이 비어있어 추가에 실패했습니다.")
                else {
                    restService.createTask(
                        CreateTaskRequest(
                            name = name,
                            start_time = start_time,
                            end_time = end_time
                        ), date = date
                    )
                    toaster.toast("todo를 추가했습니다.")
                }
            } catch (e: HttpException) {
                toaster.toastApiError(e)
            } catch (e: Exception) {

            }
        }
    }

    suspend fun checkTodo(tid: Int) {
        viewModelScope.launch {
            try {
                val task = restService.checkTask(
                    tid = tid
                )
                if (task.complete) toaster.toast("todo를 완료했습니다.")
                else toaster.toast("todo를 다시 진행합니다.")
            } catch (e: HttpException) {
                toaster.toastApiError(e)
            } catch (e: Exception) {

            }
        }
    }

    suspend fun deleteTodo(tid: Int) {
        viewModelScope.launch {
            try {
                restService.deleteTask(
                    tid = tid
                )
                toaster.toast("todo를 삭제했습니다.")
            } catch (e: NullPointerException) {
                toaster.toast("todo를 삭제했습니다.")
            } catch (e: HttpException) {
                toaster.toastApiError(e)
            } catch (e: Exception) {

            }
        }
    }

    suspend fun delayTodo(tid: Int) {
        viewModelScope.launch {
            try {
                restService.delayTask(
                    tid = tid
                )
                toaster.toast("todo를 하루 미뤘습니다.")
            } catch (e: NullPointerException) {

            } catch (e: HttpException) {
                toaster.toastApiError(e)
            } catch (e: Exception) {

            }
        }
    }

    suspend fun changeTodo(
        name: String,
        date: String,
        start_time: String,
        end_time: String,
        tid: Int
    ) {
        viewModelScope.launch {
            try {
                if (name == "") toaster.toast("이름이 비어있어 수정에 실패했습니다.")
                else {
                    restService.changeTask(
                        ChangeTaskRequest(
                            name = name,
                            date = date,
                            start_time = start_time,
                            end_time = end_time
                        ),
                        tid = tid
                    )
                    toaster.toast("todo를 수정했습니다.")
                }
            } catch (e: HttpException) {
                toaster.toastApiError(e)
            } catch (e: Exception) {

            }
        }
    }
}