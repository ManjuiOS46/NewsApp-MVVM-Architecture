package com.joydipbhakat.newsapp.ui.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joydipbhakat.newsapp.data.models.NewsInfo
import com.joydipbhakat.newsapp.databinding.ActivityLanguageBinding
import com.joydipbhakat.newsapp.ui.newslist.NewsListActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class LanguageActivity : AppCompatActivity() {

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var languageAdapter: LanguageAdapter

    @Inject
    lateinit var gson: Gson

    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpData()
        setUpUi()
    }

    private fun loadJSON(): String? {
        return try {
            context.assets.open("language.json").bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    private fun setUpData() {
        val jsonString = loadJSON()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val languageMap: Map<String, String> = gson.fromJson(jsonString, type)
        val languageList = languageMap.toList()
        languageAdapter.addData(languageList.map { it.second })
        languageAdapter.setItemClickListener { firsLanguage, secondLanguage ->
            val firstLanguageSelected = languageList.find { it.second == firsLanguage }?.first
            val secondLanguageSelected = languageList.find { it.second == secondLanguage}?.first
            val newsInfo = NewsInfo(
                "Language",
                null,
                firstLanguageSelected.toString(),
                secondLanguageSelected.toString()
            )
            val intent = Intent(context, NewsListActivity::class.java)
            intent.putExtra("news_info", newsInfo)
            startActivity(intent)
        }
    }



    private fun setUpUi() {
        binding.languageRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.languageRecyclerView.adapter = languageAdapter
    }

}