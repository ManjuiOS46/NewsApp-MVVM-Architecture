package com.joydipbhakat.newsapp.ui.search

import com.joydipbhakat.newsapp.ui.UIState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.ui.component.ErrorView
import com.joydipbhakat.newsapp.ui.component.LoadingView
import com.joydipbhakat.newsapp.ui.component.NewsListView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowSearchPage()
        }
    }
}

@Composable
private fun ShowSearchPage() {
    var text by remember { mutableStateOf("") }
    val searchViewModel: SearchViewModel = hiltViewModel()
    Column {
        ShowTextField(
            text = text,
            onTextChange = { text = it },
            searchViewModel
        )
        SearchScreen(text, searchViewModel)
    }
}

@Composable
private fun SearchScreen(text: String, searchViewModel: SearchViewModel) {
    when (val uiState = searchViewModel.uiState.collectAsState().value) {
        is UIState.Success -> {
            val newsList = uiState.data.collectAsState(initial = emptyList())
            NewsListView(data = newsList.value)
        }
        is UIState.Error -> {
            ErrorView {
                searchViewModel.newsSearch(text)
            }
        }
        is UIState.Loading -> {
            LoadingView()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ShowTextField(
    text: String,
    onTextChange: (String) -> Unit,
    searchViewModel: SearchViewModel
) {
    TextField(
        value = text,
        onValueChange = {
            onTextChange(it)
            if (it.isNotEmpty()) {
                searchViewModel.newsSearch(it)
            }
        },
        label = { Text(stringResource(R.string.search)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    )
}