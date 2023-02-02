package com.example.todomateclone.ui.user

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.todomateclone.network.RestService
import com.example.todomateclone.network.dto.BlockingDTO
import com.example.todomateclone.network.dto.FolloweeDTO
import com.example.todomateclone.network.dto.TaskDTO
import com.kakao.auth.StringSet.api

class BlockingPagingSource(
    private val restService: RestService,
) : PagingSource<Int, BlockingDTO>() {

    override fun getRefreshKey(state: PagingState<Int, BlockingDTO>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BlockingDTO> {
        val page = params.key ?: 1
        val response = if(page==1) restService.getBlockingFirstPage() else restService.getBlockingPaged(page)
        val nextPage = if(response.next==null) null else page + 1
        val prevPage = if(response.previous==null || page==1) null else page-1
        val blockingList = response.results
        return LoadResult.Page(
            data = blockingList,
            prevKey = prevPage,
            nextKey = nextPage
        )
    }

}