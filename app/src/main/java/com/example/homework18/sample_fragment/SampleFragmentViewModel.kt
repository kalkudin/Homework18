package com.example.homework18.sample_fragment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.homework18.model.User
import com.example.homework18.paging.UserPagingSource
import kotlinx.coroutines.flow.*

class SampleFragmentViewModel : ViewModel() {

    //could use a cold flow here instead, but i am more comfortable with using StateFlow it makes more sense to me
    companion object {
        private const val TAG = "SampleFragmentViewModel"
    }

    private val _users = MutableStateFlow<PagingData<User>>(PagingData.empty())
    val users: StateFlow<PagingData<User>> = _users

    init {
        fetchData()
    }

    private fun fetchData() {
        Pager(
            config = PagingConfig(pageSize = 6),
            pagingSourceFactory = { UserPagingSource() }
        ).flow
            .cachedIn(viewModelScope)
            .onEach { pagingData ->
                Log.d(TAG, "Flow emits: $pagingData")
                _users.value = pagingData
            }
            .launchIn(viewModelScope)
    }
}