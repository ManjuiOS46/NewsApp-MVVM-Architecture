package com.joydipbhakat.newsapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.databinding.ActivityMainBinding
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.component.DaggerTopHeadlineComponent
import com.joydipbhakat.newsapp.di.component.TopHeadlineComponent
import com.joydipbhakat.newsapp.di.module.TopHeadlineActivityModule
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    @ActivityScope
    private lateinit var topHeadlineComponent: TopHeadlineComponent

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: TopHeadlineViewModelFactory

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    private lateinit var topHeadlineViewModel: TopHeadlineViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        topHeadlineViewModel =
            ViewModelProvider(this, viewModelFactory)[TopHeadlineViewModel::class.java]
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
                        is UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UIState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            topHeadlineAdapter.addData(it.list)
                        }
                        is UIState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this@TopHeadlineActivity, it.message, Toast.LENGTH_SHORT)
                                .show()

                        }
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