package com.joydipbhakat.newsapp.ui.newssources

import com.joydipbhakat.newsapp.ui.UIState
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.data.models.NewsSources
import com.joydipbhakat.newsapp.ui.component.ErrorView
import com.joydipbhakat.newsapp.ui.component.LoadingView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsSourcesActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsSourcesScreen()
        }
    }
}
@Composable
private fun NewsSourcesScreen() {
    val newsSourcesViewModel: NewsSourcesViewModel = hiltViewModel()
    when (val uiState = newsSourcesViewModel.uiState.collectAsState().value) {
        is UIState.Success -> {
            NewsSources(uiState.data)
        }
        is UIState.Error -> {
            ErrorView {
                newsSourcesViewModel.fetchNewsSources()
            }
        }
        is UIState.Loading -> {
            LoadingView()
        }
    }
}

@Composable
fun NewsSources(data: List<NewsSources>) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Column {
            Text(text = stringResource(id = R.string.news_sources),
                modifier = Modifier.padding(8.dp,16.dp,8.dp,8.dp),
                fontSize = 32.sp)
            LazyColumn {
                items(data) { item ->
                    Button(onClick = {
                        val customTabsIntent = CustomTabsIntent
                            .Builder()
                            .setShowTitle(true)
                            .build()

                        customTabsIntent.launchUrl(
                            context,
                            Uri.parse(item.url)
                        )
                    },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                            .padding(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.purple_200)
                        ),
                    shape = RectangleShape) {
                        Text(text = item.name.toString())
                    }
                }
            }
        }
    }
}
