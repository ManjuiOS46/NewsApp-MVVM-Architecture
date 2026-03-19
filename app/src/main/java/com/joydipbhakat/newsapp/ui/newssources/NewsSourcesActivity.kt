package com.joydipbhakat.newsapp.ui.newssources

import UIState
import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.BaseActivity
import com.joydipbhakat.newsapp.databinding.ActivityNewssourcesBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class NewsSourcesActivity : BaseActivity() {

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var newsSourcesAdapter: NewsSourcesAdapter

    private val  newsSourcesViewModel: NewsSourcesViewModel by viewModels()

    private lateinit var binding: ActivityNewssourcesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewssourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUI()
        binding.errorLayout.retryButton.setOnClickListener {
            newsSourcesViewModel.fetchNewsSources()
        }
        setUpObserver()
    }

    private fun setUpUI() {
        binding.newsSourcesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newsSourcesRecyclerView.adapter = newsSourcesAdapter
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourcesViewModel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            showSuccess(binding.newsSourcesProgressBar, binding.errorLayout.root)
                            newsSourcesAdapter.addData(it.data)
                        }
                        is UIState.Error -> showError(
                            binding.newsSourcesProgressBar,
                            binding.errorLayout.root
                        )
                        UIState.Loading -> showLoading(
                            binding.newsSourcesProgressBar,
                            binding.errorLayout.root
                        )
                        else -> {
                        }
                    }
                }
            }
        }

    }
}