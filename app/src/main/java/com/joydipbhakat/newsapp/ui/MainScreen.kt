package com.joydipbhakat.newsapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.ui.navigation.Routes

@Composable
fun MainScreen(navController: NavHostController) {

    // 1. Define all nav items as data — add/remove here only
    val navItems = remember {
        listOf(
            R.string.top_headline_pagination to Routes.TopHeadlinePagination.routes,
            R.string.offline_articles to Routes.OfflineArticles.routes,
            R.string.top_headlines to Routes.TopHeadline.routes,
            R.string.news_sources to Routes.NewsSources.routes,
            R.string.countries to Routes.Countries.routes,
            R.string.languages to Routes.Languages.routes,
            R.string.search to Routes.Search.routes,
        )
    }

    Column(modifier = Modifier.padding(top = 64.dp)) {
        navItems.forEachIndexed { index, (labelRes, route) ->
            val topPadding = if (index == 0) 100.dp else 24.dp
            NavButton(
                labelRes = labelRes,
                topPadding = topPadding,
                onClick = { navController.navigate(route) }
            )
        }
    }
}


@Composable
private fun NavButton(
    @StringRes labelRes: Int,
    topPadding: Dp,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp, top = topPadding),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF03DAC5)
        )
    ) {
        Text(text = stringResource(id = labelRes))
    }
}