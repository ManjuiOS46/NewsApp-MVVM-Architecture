package com.joydipbhakat.newsapp.ui.component


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.joydipbhakat.newsapp.data.network.models.ApiArticles

@Composable
fun NewsListScreen(data: List<ApiArticles>, onArticleClick: (String?) -> Unit = {}) {
    LazyColumn {
        items(data) { item ->
            ApiArticleItem(
                article = item,
                onArticleClick = onArticleClick
            )
        }
    }
}
