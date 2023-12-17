package com.example.homework18.paging

import android.net.http.HttpException
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.homework18.client.ApiClient
import com.example.homework18.model.User
import java.io.IOException

//it might be better to pass backend into the constructor when the object is created in the viewmodel. ill keep it like this for now
class UserPagingSource : PagingSource<Int, User>() {

    private val apiService = ApiClient.apiService

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getUsers(page, params.loadSize)

            response.data.forEach { user ->
                Log.d(
                    "UserPagingSource",
                    "User ID: ${user.id}, Email: ${user.email}, FirstName:${user.firstName}, LastName:${user.lastName}"
                )
            }

            LoadResult.Page(
                data = response.data,
                //only going forward so we dont need the prev_key here
                prevKey = null,
                //this is a bit confusing how all of this interoperates but this should return null once we reach the second page
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: Exception) {
            Log.e("UserPagingSource", "Error loading data", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? = null
}