package com.joydipbhakat.newsapp.ui.offlinearticles

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.ui.UIState
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.OfflineNewsListScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class OfflineArticlesActivity : ComponentActivity() {

    @Inject
    @ActivityContext
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OfflineArticleScreen(context)
        }
    }
}

@Composable
fun OfflineArticleScreen(context:Context) {
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