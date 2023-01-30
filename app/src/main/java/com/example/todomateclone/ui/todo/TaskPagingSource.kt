package com.example.todomateclone.ui.todo

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.TaskDTO
import com.kakao.auth.StringSet.api

class TaskPagingSource(
    private val restService: RestService,
    private val date: String
) : PagingSource<Int, TaskDTO>() {

    override fun getRefreshKey(state: PagingState<Int, TaskDTO>): Int? {
        return null
    }
//
//    override suspend fun load(params: LoadParams<String>): LoadResult<String, TaskDTO> {
//        val response = restService.getTasksByDatePaged(date = date)
////        val prevItemId = ((params.key ?: 0) - params.loadSize).let {
////            if (it > 0) it
////            else null
////        }
//        return LoadResult.Page(
//            data = response.results,
//            nextKey = response.next,
//            prevKey = response.previous
//        )
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaskDTO> {
//        val response = restService.getTasksByDatePaged(date = date)
//        // Start paging with the STARTING_KEY if this is the first load
//        val start = params.key ?: 0
//        // Load as many items as hinted by params.loadSize
//        val range = start.until(start + params.loadSize)
//
//        val prevItemId = ((params.key ?: 0) - params.loadSize).let {
//            if (it > 0) it
//            else null
//        }
//
//        return LoadResult.Page(
//            data = response.results,
//            // Make sure we don't try to load items behind the STARTING_KEY
//            prevKey = prevItemId,
//            nextKey = range.last + 1
//        )
//    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaskDTO> {
        val page = params.key ?: 1
        val response = if(page==1) restService.getTasksByDateFirstPage(date) else restService.getTasksByDatePaged(date, page)
//        val nextPage = response.next?.let { Uri.parse(it).getQueryParameter("count")?.toInt() }
//        val prevPage = response.previous?.let { Uri.parse(it).getQueryParameter("count")?.toInt() }
//        val nextPage = if(response.next==null) null else response.next.split("page=")[1].toInt()
//        val prevPage = if(response.previous==null) null else response.previous.split("page=")[1].toInt()
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