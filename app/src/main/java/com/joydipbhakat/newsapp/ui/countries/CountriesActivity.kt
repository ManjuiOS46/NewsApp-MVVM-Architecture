package com.joydipbhakat.newsapp.ui.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.data.network.models.Countries
import com.joydipbhakat.newsapp.data.network.models.NewsInfo
import com.joydipbhakat.newsapp.ui.newslist.NewsListActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class CountriesActivity : ComponentActivity() {

    @ActivityContext
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountriesScreen(loadJson())
        }
    }

    private fun loadJson(): List<Countries> {
        val jsonString = context.assets.open("countries.json")
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<List<Countries>>() {}.type
        return gson.fromJson(jsonString, type)
    }

}

@Composable
private fun CountriesScreen(data: List<Countries>) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Column {
            Text(
                text = stringResource(id = R.string.countries),
                modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 8.dp),
                fontSize = 32.sp
            )
            LazyColumn {
                items(data) { item ->
                    Button(
                        onClick = {
                            val newsInfo = NewsInfo(
                                "Countries",
                                item.code,
                                null,
                                null
                            )
                            val intent = Intent(context, NewsListActivity::class.java)
                            intent.putExtra("news_info", newsInfo)
                            context.startActivity(intent)
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(250.dp)
                            .padding(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.purple_200)
                        ),
                        shape = RectangleShape
                    ) {
                        Text(text = item.name)
                    }
                }
            }
        }
    }
}