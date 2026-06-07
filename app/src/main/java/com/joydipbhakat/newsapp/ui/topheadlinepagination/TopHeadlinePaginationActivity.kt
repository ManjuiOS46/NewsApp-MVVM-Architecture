package com.joydipbhakat.newsapp.ui.topheadlinepagination

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.joydipbhakat.newsapp.ui.component.PaginationNewsListScreen
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlinePaginationActivity : ComponentActivity() {
    @Inject
    @ActivityContext
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopHeadlinePagingScreen(context)
        }
    }
}

@Composable
fun TopHeadlinePagingScreen(context: Context) {
    val viewModel = hiltViewModel<TopHeadlinePaginationViewModel>()
    val articles = viewModel.getArticlesFromPagination().collectAsLazyPagingItems()
    PaginationNewsListScreen(articles = articles) {
        CustomTabsIntent.Builder()
            .setShowTitle(true)
            .build()
            .launchUrl(
                context,
                Uri.parse(it)
            )
    }
}