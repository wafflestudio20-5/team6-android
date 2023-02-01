package com.example.todomateclone.ui.user

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.FolloweeDTO
import com.example.todomateclone.network.dto.TaskDTO
import com.kakao.auth.StringSet.api

class FolloweePagingSource(
    private val restService: RestService,
    private val date: String
) : PagingSource<Int, FolloweeDTO>() {

    override fun getRefreshKey(state: PagingState<Int, FolloweeDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FolloweeDTO> {
        val page = params.key ?: 1
        val response = if(page==1) restService.getFolloweeFirstPage() else restService.getFolloweePaged(page)
        val nextPage = if(response.next==null) null else page + 1
        val prevPage = if(response.previous==null || page==1) null else page-1
        val followeeList = response.results
        return LoadResult.Page(
            data = followeeList,
            prevKey = prevPage,
            nextKey = nextPage
        )
    }

}