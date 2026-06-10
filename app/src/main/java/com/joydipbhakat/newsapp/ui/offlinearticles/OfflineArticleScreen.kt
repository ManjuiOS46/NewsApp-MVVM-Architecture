package com.joydipbhakat.newsapp.ui.offlinearticles

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.ui.UIState
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.OfflineNewsListScreen


@Composable
fun OfflineArticleScreen() {
    val context = LocalContext.current
    val offlineArticlesViewModel: OfflineArticlesViewModel = hiltViewModel()
    when (val uiState = offlineArticlesViewModel.uiState.collectAsState().value) {
        is UIState.Success -> {
               OfflineNewsListScreen(data = uiState.data) {
                   CustomTabsIntent.Builder()
                       .setShowTitle(true)
                       .build()
                       .launchUrl(
                           context,
                           Uri.parse(it)
                       )
               }
        }
        is UIState.Error -> {
            ErrorScreen {
               offlineArticlesViewModel.fetchArticles()
            }
        }
        is UIState.Loading -> {
            LoadingScreen()
        }
    }
}