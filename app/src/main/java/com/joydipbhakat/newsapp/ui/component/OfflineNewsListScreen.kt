package com.joydipbhakat.newsapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.joydipbhakat.newsapp.data.local.entity.Article


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OfflineNewsListScreen(data: List<Article>, onArticleClick: (String?) -> Unit = {}) {
    LazyColumn {
        items(data) { item ->
            Column {
                GlideImage(
                    model = item.urlToImage,
                    contentDescription = "androidContent",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clickable {
                            onArticleClick(item.url)
                        },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.title ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = item.description ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
                Text(
                    text = item.source.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 1,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
