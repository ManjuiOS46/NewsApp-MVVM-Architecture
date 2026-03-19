package com.joydipbhakat.newsapp.ui.search

import UIState
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.BaseActivity
import com.joydipbhakat.newsapp.databinding.ActivitySearchBinding
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineAdapter
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    private val searchViewModel: SearchViewModel by viewModels()

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchViewEditText.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                searchViewModel.newsSearch(it.toString())
            }
        }
        binding.errorLayout.retryButton.setOnClickListener {
            val textPresentOnSearchBar = binding.searchViewEditText.text.toString()
            if (textPresentOnSearchBar.isNotEmpty())
                searchViewModel.newsSearch(textPresentOnSearchBar)
        }
        setUpUi()
        setUpObserver()
    }

    private fun setUpUi() {
        binding.apply {
            searchRecyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
            searchRecyclerView.adapter = adapter
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                searchViewModel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            showSuccess(binding.searchProgressBar, binding.errorLayout.root)
                            it.data.collect { it1 ->
                                adapter.addData(it1)
                            }
                        }
                        is UIState.Error -> showError(
                            binding.searchProgressBar,
                            binding.errorLayout.root
                        )
                        is UIState.Loading -> showLoading(
                            binding.searchProgressBar,
                            binding.errorLayout.root
                        )
                        else -> {}
                    }
                }
            }
        }
    }
}