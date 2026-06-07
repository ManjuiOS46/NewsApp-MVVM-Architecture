package com.joydipbhakat.newsapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.joydipbhakat.newsapp.data.local.entity.Article

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArticleItem(
    article: Article,
    onArticleClick: (String?) -> Unit = {}
) {
    Column {
        GlideImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clickable {
                    onArticleClick(article.url)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = article.title.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.description.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.source.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}