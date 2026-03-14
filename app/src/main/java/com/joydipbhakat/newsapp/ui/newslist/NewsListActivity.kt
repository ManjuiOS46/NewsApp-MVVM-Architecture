package com.joydipbhakat.newsapp.ui.newslist

import UIState
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.BaseActivity
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.data.models.NewsInfo
import com.joydipbhakat.newsapp.databinding.ActivityNewslistBinding
import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.component.DaggerNewsListComponent
import com.joydipbhakat.newsapp.di.component.NewsListComponent
import com.joydipbhakat.newsapp.di.module.NewsListActivityModule
import com.joydipbhakat.newsapp.ui.ViewModelFactory
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsListActivity : BaseActivity() {

    @ActivityScope
    lateinit var newsListComponent: NewsListComponent

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var topHeadlineAdapter: TopHeadlineAdapter

    private lateinit var newsListViewModel: NewsListViewModel

    private lateinit var binding: ActivityNewslistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        val newsInfo = intent.getParcelableExtra<NewsInfo>("news_info")
        binding = ActivityNewslistBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsListViewModel =
            ViewModelProvider(this, viewModelFactory)[NewsListViewModel::class.java]
        setUpUi()
        if (newsInfo?.source == "Language") {
            newsInfo.let { newsListViewModel.getNewsBasedOnLanguage(it.firstLanguage.toString(),it.secondLanguage.toString())
            setUpObserverForLanguage()}
        } else {
            newsInfo?.let { newsListViewModel.getTopHeadlinesBasedOnCountry(it.countryCode.toString())
            setUpObserverForCountry()
            }
        }
        binding.errorLayout.retryButton.setOnClickListener {
            if (newsInfo?.source == "Language") {
                newsListViewModel.getNewsBasedOnLanguage(newsInfo.firstLanguage.toString(),newsInfo.secondLanguage.toString())
            } else {
                newsInfo?.countryCode?.let { it1 -> newsListViewModel.getTopHeadlinesBasedOnCountry(it1) }
            }
        }
    }

    private fun setUpUi() {
        binding.newsListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.newsListRecyclerView.adapter = topHeadlineAdapter
    }

    private fun setUpObserverForCountry() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiStateForCountry.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.newsListProgressBar.visibility = View.GONE
                            topHeadlineAdapter.addData(it.data)
                        }
                        is UIState.Error -> {
                            binding.newsListProgressBar.visibility = View.GONE
                            Toast.makeText(this@NewsListActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is UIState.Loading -> {
                            binding.newsListProgressBar.visibility = View.VISIBLE

                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun setUpObserverForLanguage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.uiStateForLanguage.collect {
                    when (it) {
                        is UIState.Success -> {
                            showSuccess(binding.newsListProgressBar, binding.errorLayout.root)
                            topHeadlineAdapter.addData(it.data)
                        }
                        is UIState.Error -> showError(binding.newsListProgressBar, binding.errorLayout.root)
                        is UIState.Loading -> showLoading(binding.newsListProgressBar, binding.errorLayout.root)
                        else -> {}
                    }
                }
            }
        }
    }

    private fun injectDependencies() {
        newsListComponent = DaggerNewsListComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .newsListActivityModule(
                NewsListActivityModule(this)
            ).build()

        newsListComponent.inject(this)
    }
}