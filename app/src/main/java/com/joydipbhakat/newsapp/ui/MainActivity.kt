package com.joydipbhakat.newsapp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.joydipbhakat.newsapp.ui.navigation.AppNavGraph
import com.joydipbhakat.newsapp.ui.theme.NewsTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @ActivityContext
    lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph()
                }
            }
        }
    }
}