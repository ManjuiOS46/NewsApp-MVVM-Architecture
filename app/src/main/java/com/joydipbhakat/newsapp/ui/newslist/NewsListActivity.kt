package com.joydipbhakat.newsapp.ui.newslist

import com.joydipbhakat.newsapp.ui.UIState
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.data.models.NewsInfo
import com.joydipbhakat.newsapp.ui.component.ErrorView
import com.joydipbhakat.newsapp.ui.component.LoadingView
import com.joydipbhakat.newsapp.ui.component.NewsListView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class NewsListActivity : ComponentActivity() {
    @Inject
    @ActivityContext
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsInfo = intent.getParcelableExtra<NewsInfo>("news_info")
        setContent {
            NewsListScreen(newsInfo = newsInfo)
        }
    }
}

@Composable
private fun NewsListScreen(newsInfo: NewsInfo?) {
    val newsListViewModel: NewsListViewModel = hiltViewModel()
    if (newsInfo?.source == "Language") {
        newsInfo.let {
            newsListViewModel.getNewsBasedOnLanguage(
                it.firstLanguage.toString(),
                it.secondLanguage.toString()
            )
            NewsForLanguage(newsInfo, newsListViewModel)
        }
    } else {
        newsInfo?.let {
            newsListViewModel.getTopHeadlinesBasedOnCountry(it.countryCode.toString())
            NewsForCountries(newsInfo, newsListViewModel)
        }
    }
}

@Composable
private fun NewsForLanguage(newsInfo: NewsInfo?, newsListViewModel: NewsListViewModel) {
    when (val uiState = newsListViewModel.uiStateForLanguage.collectAsState().value) {
        is UIState.Success -> {
            NewsListView(data = uiState.data)
        }
        is UIState.Error -> {
            ErrorView {
                newsListViewModel.getNewsBasedOnLanguage(
                    newsInfo?.firstLanguage.toString(),
                    newsInfo?.secondLanguage.toString()
                )
            }
        }
        is UIState.Loading -> {
            LoadingView()
        }
    }
}

@Composable
private fun NewsForCountries(newsInfo: NewsInfo?, newsListViewModel: NewsListViewModel) {
    when (val uiState = newsListViewModel.uiStateForCountry.collectAsState().value) {
        is UIState.Success -> {
            NewsListView(data = uiState.data)
        }
        is UIState.Error -> {
            ErrorView {
                newsInfo?.countryCode?.let { newsListViewModel.getTopHeadlinesBasedOnCountry(it) }
            }
        }
        is UIState.Loading -> {
            LoadingView()
        }
    }
}

