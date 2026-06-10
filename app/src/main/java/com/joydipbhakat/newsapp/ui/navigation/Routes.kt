package com.joydipbhakat.newsapp.ui.navigation

sealed class Routes(val routes: String) {
    object MainScreen : Routes("MainScreen")
    object TopHeadline : Routes("TopHeadline")
    object TopHeadlinePagination: Routes("TopHeadlinePagination")
    object OfflineArticles: Routes("OfflineArticles")
    object NewsSources: Routes("NewsSources")
    object Search: Routes("Search")
    object Countries: Routes("Countries")
    object Languages: Routes("Languages")
}
