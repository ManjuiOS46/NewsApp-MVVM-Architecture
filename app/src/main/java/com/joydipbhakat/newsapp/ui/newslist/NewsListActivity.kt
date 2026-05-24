package com.joydipbhakat.newsapp.ui.newslist

import com.joydipbhakat.newsapp.ui.UIState
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.data.models.NewsInfo
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.NewsListScreen
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
            NewsListScreen(newsInfo = newsInfo, context)
        }
    }
}

@Composable
private fun NewsListScreen(newsInfo: NewsInfo?, context: Context) {
    val newsListViewModel: NewsListViewModel = hiltViewModel()
    if (newsInfo?.source == "Language") {
        newsInfo.let {
            newsListViewModel.getNewsBasedOnLanguage(
                it.firstLanguage.toString(),
                it.secondLanguage.toString()
            )
            NewsForLanguage(newsInfo, newsListViewModel, context)
        }
    } else {
        newsInfo?.let {
            newsListViewModel.getTopHeadlinesBasedOnCountry(it.countryCode.toString())
            NewsForCountries(newsInfo, newsListViewModel, context)
        }
    }
}

@Composable
private fun NewsForLanguage(newsInfo: NewsInfo?, newsListViewModel: NewsListViewModel, context: Context) {
    when (val uiState = newsListViewModel.uiStateForLanguage.collectAsState().value) {
        is UIState.Success -> {
            NewsListScreen(data = uiState.data){ url ->
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                    .launchUrl(
                        context,
                        Uri.parse(url)
                    )
            }
        }
        is UIState.Error -> {
            ErrorScreen {
                newsListViewModel.getNewsBasedOnLanguage(
                    newsInfo?.firstLanguage.toString(),
                    newsInfo?.secondLanguage.toString()
                )
            }
        }
        is UIState.Loading -> {
            LoadingScreen()
        }
    }
}

@Composable
private fun NewsForCountries(newsInfo: NewsInfo?, newsListViewModel: NewsListViewModel, context: Context) {
    when (val uiState = newsListViewModel.uiStateForCountry.collectAsState().value) {
        is UIState.Success -> {
            NewsListScreen(data = uiState.data){ url ->
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                    .launchUrl(
                        context,
                        Uri.parse(url)
                    )
            }
        }
        is UIState.Error -> {
            ErrorScreen {
                newsInfo?.countryCode?.let { newsListViewModel.getTopHeadlinesBasedOnCountry(it) }
            }
        }
        is UIState.Loading -> {
            LoadingScreen()
        }
    }
}

