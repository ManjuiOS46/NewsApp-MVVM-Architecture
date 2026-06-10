package com.joydipbhakat.newsapp.ui.topheadlinepagination

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.joydipbhakat.newsapp.ui.component.PaginationNewsListScreen

@Composable
fun TopHeadlinePaginationScreen() {
    val context  = LocalContext.current
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