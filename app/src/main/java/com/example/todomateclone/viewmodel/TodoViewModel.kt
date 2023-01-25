package com.example.todomateclone.viewmodel

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.CreateTaskRequest
import com.example.todomateclone.network.dto.TaskDTO
import com.example.todomateclone.ui.TaskPagingSource
import com.example.todomateclone.util.Toaster
import com.kakao.usermgmt.StringSet.name
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalDate

class TodoViewModel(
    private val restService: RestService,
    private val toaster: Toaster
    ) : ViewModel() {

    fun createPager(date: String): Flow<PagingData<TaskDTO>> {
        return Pager(PagingConfig(pageSize = 10)) {
            TaskPagingSource(restService, date)
        }.flow.cachedIn(viewModelScope)
    }


    fun createTodo(name: String, date: String) {
        viewModelScope.launch {
            try {
                restService.createTask(
                    CreateTaskRequest(
                        name = name
                    ), date = date
                )
                toaster.toast("Successfully created.")
            } catch (e: Exception) {
                toaster.toastApiError(e)
            }
        }
    }

    fun checkTodo(tid: Int) {
        viewModelScope.launch {
            try {
                restService.checkTask(
                    tid = tid
                )
                toaster.toast("Successfully created.")
            } catch (e: Exception) {
                toaster.toastApiError(e)
            }
        }
    }

    fun deleteTodo(tid: Int) {
        viewModelScope.launch {
            try {
                restService.deleteTask(
                    tid = tid
                )
                toaster.toast("Successfully created.")
            } catch (e: NullPointerException) {

            } catch (e: Exception) {
                toaster.toastApiError(e)
            }
        }
    }

//    private fun getUpdatedTodoEntry(id: Long, title: String, content: String, done: Int): Todo {
//        return Todo(
//            id = id,
//            title = title,
//            content = content,
//            createdAt = 0, //WIP
//            done = done
//        )
//    }
//
//    suspend fun updateTodo(id: Long, title: String, content: String, done: Int) {
//        val up = getUpdatedTodoEntry(id, title, content, done)
//        viewModelScope.launch {
//            todoDao.update(up)
//        }
//    }
//
//    fun toggleTodo(id: Long, title: String, content: String, done: Int) {
//        val up = getUpdatedTodoEntry(id, title, content, 1-done)
//        viewModelScope.launch {
//            todoDao.update(up)
//        }
//    }
//
//    fun addNewTodo(title: String, content: String) {
//        val newTodo = getNewTodoEntry(title, content)
//        insertTodo(newTodo)
//    }
//
//
//    fun isEntryValid(title: String, content: String): Boolean {
//        if (title.isBlank() || content.isBlank()) {
//            return false
//        }
//        return true
//    }

//    fun retrieveTodo(id: Int): LiveData<Todo> {
//        return todoDao.getTodo(id).asLiveData()
//    }

//    suspend fun deleteTodo(todoId: Int) {
//        try {
//            restService.deletePost(todoId)
//        } catch (e: Exception) {
//            toaster.toastApiError(e)
//        }
//    }


}

//
//class TodoModelFactory(private val todoDao: TodoDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return TodoViewModel(todoDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}
