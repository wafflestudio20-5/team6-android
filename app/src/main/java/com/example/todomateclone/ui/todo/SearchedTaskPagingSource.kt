package com.example.todomateclone.ui.todo

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.TaskDTO
import com.kakao.auth.StringSet.api

class SearchedTaskPagingSource(
    private val restService: RestService,
    private val date: String,
    private val uid: Int,
) : PagingSource<Int, TaskDTO>() {

    override fun getRefreshKey(state: PagingState<Int, TaskDTO>): Int? {
        return null
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaskDTO> {
        val page = params.key ?: 1
        val response = if(page==1) restService.getSearchedTasksByDateFirstPage(date, uid) else restService.getSearchedTasksByDatePaged(date, uid, page)
        val nextPage = if(response.next==null) null else page + 1
        val prevPage = if(response.previous==null || page==1) null else page-1
        val taskList = response.results
        return LoadResult.Page(
            data = taskList,
            prevKey = prevPage,
            nextKey = nextPage
        )
    }

//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Task> {
//        val page = params.key ?: 1
//        val response = api.getTaskList(page)
//        val taskList = response.taskList
//        return LoadResult.Page(
//            data = taskList,
//            prevKey = if (page == 1) null else page - 1,
//            nextKey = page + 1
//        )
//    }
}