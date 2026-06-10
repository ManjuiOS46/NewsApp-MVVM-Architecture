package com.joydipbhakat.newsapp.ui.topheadline

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.ui.UIState
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.NewsListScreen

@Composable
fun TopHeadlineScreen() {
    val context = LocalContext.current
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

