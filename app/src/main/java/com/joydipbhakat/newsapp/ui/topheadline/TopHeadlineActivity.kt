package com.joydipbhakat.newsapp.ui.topheadline

import com.joydipbhakat.newsapp.ui.UIState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.ui.component.ErrorView
import com.joydipbhakat.newsapp.ui.component.LoadingView
import com.joydipbhakat.newsapp.ui.component.NewsListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopHeadlineActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopHeadlineScreen()
        }
    }
}

@Composable
private fun TopHeadlineScreen() {
    val topHeadlineViewModel: TopHeadlineViewModel = hiltViewModel()
    when (val uiState = topHeadlineViewModel.uiState.collectAsState().value) {
        is UIState.Success -> {
           NewsListView(data = uiState.data)
        }
        is UIState.Error -> {
            ErrorView { topHeadlineViewModel.fetchNews() }
        }
        is UIState.Loading -> {
            LoadingView()
        }
    }
}

