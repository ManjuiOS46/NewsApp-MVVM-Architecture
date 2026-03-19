package com.joydipbhakat.newsapp.ui.topheadline

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.BaseActivity
import com.joydipbhakat.newsapp.databinding.ActivityTopheadlineBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlineActivity : BaseActivity() {

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    private val topHeadlineViewModel: TopHeadlineViewModel by viewModels()
    private lateinit var binding: ActivityTopheadlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopheadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.errorLayout.retryButton.setOnClickListener {
            topHeadlineViewModel.fetchNews()
        }
        setupUI()
        setupObserver()
    }

    private fun setupUI() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = topHeadlineAdapter
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlineViewModel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            showSuccess(binding.progressBar,binding.errorLayout.root)
                            topHeadlineAdapter.addData(it.data)
                        }
                        is UIState.Error -> {
                            showError(binding.progressBar,binding.errorLayout.root)

                        }
                        UIState.Loading -> {
                            showLoading(binding.progressBar,binding.errorLayout.root)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}