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
import com.joydipbhakat.newsapp.data.network.models.ApiArticles

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ApiArticleItem(
    article: ApiArticles,
    onArticleClick: (String?) -> Unit = {}
) {
    Column {
        GlideImage(
            model = article.urlToImage,
            contentDescription = "androidContent",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clickable {
                    onArticleClick(article.url)
                },
            contentScale = ContentScale.Crop
        )

        Text(
            text = article.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.description ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = article.apiSource?.name ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}