package com.joydipbhakat.newsapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.ui.countries.CountriesActivity
import com.joydipbhakat.newsapp.ui.language.LanguageActivity
import com.joydipbhakat.newsapp.ui.newssources.NewsSourcesActivity
import com.joydipbhakat.newsapp.ui.search.SearchActivity
import com.joydipbhakat.newsapp.ui.theme.NewsTheme
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineActivity
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
                    NewsApp()
                }
            }
        }
    }

    @Composable
    fun NewsApp() {
        Column(modifier = Modifier.padding(top = 132.dp)) {
            Button(
                onClick = { startActivity(Intent(context, TopHeadlineActivity::class.java)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 100.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF03DAC5) // teal_200
                )
            ) {
                Text(text = stringResource(id = R.string.top_headlines))
            }

            Button(
                onClick = { startActivity(Intent(context, NewsSourcesActivity::class.java)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF03DAC5) // teal_200
                )
            ) {
                Text(text = stringResource(id = R.string.news_sources))
            }

            Button(
                onClick = { startActivity(Intent(context, CountriesActivity::class.java)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF03DAC5) // teal_200
                )
            ) {
                Text(text = stringResource(id = R.string.countries))
            }

            Button(
                onClick = { startActivity(Intent(context, LanguageActivity::class.java)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF03DAC5) // teal_200
                )
            ) {
                Text(text = stringResource(id = R.string.languages))
            }

            Button(
                onClick = { startActivity(Intent(context, SearchActivity::class.java)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp, end = 32.dp, top = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF03DAC5) // teal_200
                )
            ) {
                Text(text = stringResource(id = R.string.search))
            }
        }
    }

    @Composable
    @Preview
    fun NewsAppPreview() {
        NewsTheme {
            Surface {
                NewsApp()
            }
        }
    }
}