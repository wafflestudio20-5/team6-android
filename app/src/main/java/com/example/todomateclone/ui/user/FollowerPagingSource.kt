package com.example.todomateclone.ui.user

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.FolloweeDTO
import com.example.todomateclone.network.dto.FollowerDTO
import com.example.todomateclone.network.dto.TaskDTO
import com.kakao.auth.StringSet.api

class FollowerPagingSource(
    private val restService: RestService,
) : PagingSource<Int, FollowerDTO>() {

    override fun getRefreshKey(state: PagingState<Int, FollowerDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FollowerDTO> {
        val page = params.key ?: 1
        val response = if(page==1) restService.getFollowerFirstPage() else restService.getFollowerPaged(page)
        val nextPage = if(response.next==null) null else page + 1
        val prevPage = if(response.previous==null || page==1) null else page-1
        val followerList = response.results
        return LoadResult.Page(
            data = followerList,
            prevKey = prevPage,
            nextKey = nextPage
        )
    }

}