package com.joydipbhakat.newsapp.ui.language

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.data.network.models.NewsInfo
import com.joydipbhakat.newsapp.ui.newslist.NewsListActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class LanguageActivity : ComponentActivity() {

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageScreen(loadJSON())
        }
    }

    private fun loadJSON(): List<Pair<String, String>> {
        val jsonString = context.assets.open("language.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<Map<String, String>>() {}.type
        val languageMap: Map<String, String> = gson.fromJson(jsonString, type)
        return languageMap.toList()
    }
}

    @Composable
    private fun LanguageScreen(loadJSON: List<Pair<String, String>>) {
        val context = LocalContext.current
        val data = loadJSON.map { it.second }
        val selectedLanguage = remember { mutableStateListOf<String>() }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Column {
                Text(
                    text = stringResource(id = R.string.languages),
                    modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 8.dp),
                    fontSize = 32.sp
                )
                LazyColumn {
                    items(data) { item ->
                        val isSelected = item in selectedLanguage
                        Row(verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {

                            if (isSelected) {
                                Icon(
                                    painter = painterResource(id = R.drawable.check),
                                    contentDescription = "Selected",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .padding(end = 12.dp)
                                )
                            } else {
                                Spacer(modifier = Modifier.size(24.dp).padding(end = 12.dp))
                            }

                            Button(
                                onClick = {
                                    if (isSelected) {
                                        selectedLanguage.remove(item)
                                    } else {
                                        if (selectedLanguage.size < 2) {
                                            selectedLanguage.add(item)
                                        }
                                    }
                                    if (selectedLanguage.size == 2) {
                                        val firstLanguageSelected =
                                            loadJSON.find { it.second == selectedLanguage.toList()[0] }?.first
                                        val secondLanguageSelected =
                                            loadJSON.find { it.second == selectedLanguage.toList()[1] }?.first
                                        val newsInfo = NewsInfo(
                                            "Language",
                                            null,
                                            firstLanguageSelected.toString(),
                                            secondLanguageSelected.toString()
                                        )
                                        val intent = Intent(context, NewsListActivity::class.java)
                                        intent.putExtra("news_info", newsInfo)
                                        context.startActivity(intent)
                                    }
                                },
                                modifier = Modifier
                                    .width(250.dp)
                                    .padding(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(id = R.color.purple_200)
                                ),
                                shape = RectangleShape
                            )
                            {
                                Text(text = item)
                            }
                        }

                    }
                }
            }
        }
    }