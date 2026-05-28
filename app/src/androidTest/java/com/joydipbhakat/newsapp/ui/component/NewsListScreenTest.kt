package com.joydipbhakat.newsapp.ui.component

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.network.models.ApiSource
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NewsListScreenTest {
    
    @get:Rule
    val composeRule = createComposeRule()


    @Test
    fun newsList_whenListOfArticleIsPresent_shouldShowListArticles(){
        val apiArticles = ApiArticles(
            title = "Breaking News",
            description = "Description",
            url = "https://google.com",
            urlToImage = "image",
            apiSource =
            ApiSource(
                id = "1",
                name = "BBC"
            )
        )
        composeRule.setContent { 
            NewsListScreen(data = listOf(apiArticles))
        }
        composeRule.onNodeWithText("Breaking News").assertIsDisplayed()
        composeRule.onNodeWithText("Description").assertIsDisplayed()
        composeRule.onNodeWithText("BBC").assertIsDisplayed()
    }
    
    @Test
    fun emptyList_whenEmptyArticle_shouldNotDisplayArticle()
    {
        composeRule.setContent { 
           NewsListScreen(data = emptyList())
        }
        composeRule.onAllNodes(hasText("BreakingNews")).assertCountEquals(0)
    }

    @Test
    fun newsListScreen_whenImageClick_shouldTriggerCallback()
    {
        var clickedUrl: String? = null
        val apiArticles = ApiArticles(
            title = "Breaking News",
            description = "Description",
            url = "https://google.com",
            urlToImage = "image",
            apiSource =
            ApiSource(
                id = "1",
                name = "BBC"
            )
        )
        composeRule.setContent {
            NewsListScreen(
                data = listOf(apiArticles),

                onArticleClick = {
                    clickedUrl = it
                }
            )
        }
        composeRule.onNodeWithContentDescription("androidContent").performClick()
        assertEquals(
            "https://google.com",
            clickedUrl
        )
    }
}