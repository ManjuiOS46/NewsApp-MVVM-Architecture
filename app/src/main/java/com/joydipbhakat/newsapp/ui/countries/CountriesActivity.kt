package com.joydipbhakat.newsapp.ui.countries

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.data.models.Countries
import com.joydipbhakat.newsapp.data.models.NewsInfo
import com.joydipbhakat.newsapp.databinding.ActivityCountriesBinding
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.component.CountriesComponent
import com.joydipbhakat.newsapp.di.component.DaggerCountriesComponent
import com.joydipbhakat.newsapp.di.module.CountriesActivityModule
import com.joydipbhakat.newsapp.ui.newslist.NewsListActivity
import javax.inject.Inject

class CountriesActivity : AppCompatActivity() {

    @ActivityScope
    private lateinit var countriesComponent: CountriesComponent

    @ActivityContext
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var adapter: CountriesAdapter

    @Inject
    lateinit var gson: Gson

    private lateinit var binding: ActivityCountriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpData()
        setUpUI()
    }

    private fun loadJson(): String {
        return context.assets.open("countries.json")
            .bufferedReader()
            .use { it.readText() }
    }

    private fun setUpUI() {
        binding.countriesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.countriesRecyclerView.adapter = adapter
    }

    private fun setUpData() {
        val jsonString = loadJson()
        val type = object : TypeToken<List<Countries>>() {}.type
        val list: List<Countries> = gson.fromJson(jsonString, type)
        adapter.addData(list)
        adapter.setItemClickListener {
            val newsInfo = NewsInfo(
                "Countries",
                it.name,
                it.code
            )
            val intent = Intent(context, NewsListActivity::class.java)
            intent.putExtra("news_info", newsInfo)
            startActivity(intent)
        }
    }


    fun inject() {
        countriesComponent = DaggerCountriesComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .countriesActivityModule(CountriesActivityModule(this))
            .build()


        countriesComponent.inject(this)
    }
}