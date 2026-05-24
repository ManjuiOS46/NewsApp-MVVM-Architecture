package com.joydipbhakat.newsapp.ui.search

import android.content.Context
import android.net.Uri
import com.joydipbhakat.newsapp.ui.UIState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.ui.component.ErrorScreen
import com.joydipbhakat.newsapp.ui.component.LoadingScreen
import com.joydipbhakat.newsapp.ui.component.NewsListScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : ComponentActivity() {
    @Inject
    @ActivityContext
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowSearchPage(context)
        }
    }
}

@Composable
private fun ShowSearchPage(context: Context) {
    var text by remember { mutableStateOf("") }
    val searchViewModel: SearchViewModel = hiltViewModel()
    Column {
        ShowTextField(
            text = text,
            onTextChange = { text = it },
            searchViewModel
        )
        SearchScreen(text, searchViewModel, context)
    }
}

@Composable
private fun SearchScreen(text: String, searchViewModel: SearchViewModel, context: Context) {
    when (val uiState = searchViewModel.uiState.collectAsState().value) {
        is UIState.Success -> {
            val newsList = uiState.data.collectAsState(initial = emptyList())
            NewsListScreen(data = newsList.value){ url ->
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
                searchViewModel.newsSearch(text)
            }
        }
        is UIState.Loading -> {
            LoadingScreen()
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