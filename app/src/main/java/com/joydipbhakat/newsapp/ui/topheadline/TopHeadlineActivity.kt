package com.joydipbhakat.newsapp.ui.topheadline

import android.content.Context
import android.net.Uri
import com.joydipbhakat.newsapp.ui.UIState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.NewsListScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlineActivity : ComponentActivity() {
    @Inject
    @ActivityContext
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopHeadlineScreen(context)
        }
    }
}

@Composable
private fun TopHeadlineScreen(context: Context) {
    val topHeadlineViewModel: TopHeadlineViewModel = hiltViewModel()
    when (val uiState = topHeadlineViewModel.uiState.collectAsState().value) {
        is UIState.Success -> {
            NewsListScreen(data = uiState.data) { url ->
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
            ErrorScreen { topHeadlineViewModel.fetchNews() }
        }
        is UIState.Loading -> {
            LoadingScreen()
        }
    }
}

