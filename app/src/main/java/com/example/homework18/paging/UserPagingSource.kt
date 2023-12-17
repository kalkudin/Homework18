package com.example.homework18.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework18.client.ApiClient
import com.example.homework18.model.User

class UserPagingSource : PagingSource<Int, User>() {

    private val apiService = ApiClient.apiService

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getUsers(page, params.loadSize)

            response.data.forEach { user ->
                Log.d("UserPagingSource", "User ID: ${user.id}, Email: ${user.email}, FirstName:${user.firstName}, LastName:${user.lastName}")
            }

            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            Log.e("UserPagingSource", "Error loading data", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = null
}