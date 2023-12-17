package com.example.homework18.sample_fragment

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework18.base_fragment.BaseFragment
import com.example.homework18.databinding.FragmentSampleBinding
import com.example.homework18.paging.UserPagingRecycleAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SampleFragment : BaseFragment<FragmentSampleBinding>(FragmentSampleBinding::inflate) {

    private val viewModel: SampleFragmentViewModel by viewModels()

    private val adapter by lazy {
        UserPagingRecycleAdapter()
    }

    override fun setUp() {
        observeViewModel()
        setupRecyclerView()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.users.collectLatest { pagingData ->
                Log.d(TAG, "ViewModel emits: $pagingData")
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.pagingRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.pagingRecyclerView.adapter = adapter
    }

    companion object {
        private const val TAG = "SampleFragment"
    }
}