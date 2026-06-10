package com.joydipbhakat.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joydipbhakat.newsapp.ui.MainScreen
import com.joydipbhakat.newsapp.ui.countries.CountriesScreen
import com.joydipbhakat.newsapp.ui.language.LanguageScreen
import com.joydipbhakat.newsapp.ui.newssources.NewsSourcesScreen
import com.joydipbhakat.newsapp.ui.offlinearticles.OfflineArticleScreen
import com.joydipbhakat.newsapp.ui.search.SearchScreen
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineScreen
import com.joydipbhakat.newsapp.ui.topheadlinepagination.TopHeadlinePaginationScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MainScreen.routes
    ) {

        composable(Routes.MainScreen.routes) {
            MainScreen(navController)
        }

        composable(Routes.TopHeadline.routes) {
            TopHeadlineScreen()
        }

        composable(Routes.TopHeadlinePagination.routes) {
            TopHeadlinePaginationScreen()
        }

        composable(Routes.OfflineArticles.routes) {
            OfflineArticleScreen()
        }

        composable(Routes.NewsSources.routes) {
          NewsSourcesScreen()
        }

        composable(Routes.Search.routes) {
           SearchScreen()
        }

        composable(Routes.Countries.routes) {
            CountriesScreen()
        }

        composable(Routes.Languages.routes) {
            LanguageScreen()
        }


    }
}