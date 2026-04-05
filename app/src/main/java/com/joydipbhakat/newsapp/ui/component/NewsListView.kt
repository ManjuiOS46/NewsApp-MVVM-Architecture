package com.joydipbhakat.newsapp.ui.component

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.joydipbhakat.newsapp.data.models.Articles

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsListView(data: List<Articles>) {
    val context = LocalContext.current
    LazyColumn {
        items(data) { item ->
            Column {
                GlideImage(
                    model = item.urlToImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clickable {
                            val customTabsIntent = CustomTabsIntent
                                .Builder()
                                .setShowTitle(true)
                                .build()

                            customTabsIntent.launchUrl(
                                context,
                                Uri.parse(item.url)
                            )
                        },
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.title.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Text(
                    text = item.description.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
                Text(
                    text = item.source?.name.toString(),
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
