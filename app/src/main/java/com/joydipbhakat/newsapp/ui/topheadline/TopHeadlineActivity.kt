package com.joydipbhakat.newsapp.ui.topheadline

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.BaseActivity
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.databinding.ActivityTopheadlineBinding
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.component.DaggerTopHeadlineComponent
import com.joydipbhakat.newsapp.di.component.TopHeadlineComponent
import com.joydipbhakat.newsapp.di.module.TopHeadlineActivityModule
import com.joydipbhakat.newsapp.ui.ViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : BaseActivity() {

    @ActivityScope
    private lateinit var topHeadlineComponent: TopHeadlineComponent

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    private lateinit var topHeadlineViewModel: TopHeadlineViewModel
    private lateinit var binding: ActivityTopheadlineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        binding = ActivityTopheadlineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        topHeadlineViewModel =
            ViewModelProvider(this, viewModelFactory)[TopHeadlineViewModel::class.java]
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

    private fun inject() {
        val appComponent =
            (application as NewsApplication).applicationComponent
        topHeadlineComponent = DaggerTopHeadlineComponent.builder()
            .applicationComponent(appComponent)
            .topHeadlineActivityModule(
                TopHeadlineActivityModule(this)
            ).build()
        topHeadlineComponent.inject(this)
    }
}