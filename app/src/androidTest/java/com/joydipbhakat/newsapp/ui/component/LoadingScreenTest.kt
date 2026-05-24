package com.joydipbhakat.newsapp.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class LoadingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingScreen_whenLoadingOccurs_shouldDisplaySpinner() {
        composeTestRule.setContent {
            LoadingScreen()
        }
        composeTestRule.onNodeWithTag("loading_spinner").assertIsDisplayed()
    }

}