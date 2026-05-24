package com.joydipbhakat.newsapp.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ErrorScreenTest {
    @get:Rule
    var composeRule = createComposeRule()

    @Test
    fun errorScreen_whenErrorOccurred_shouldDisplayErrorScreen() {
        composeRule.setContent {
            ErrorScreen {

            }
        }
        composeRule.onNodeWithText("Error Occurred").assertIsDisplayed()
        composeRule.onNodeWithText("Retry").performClick()
    }
}