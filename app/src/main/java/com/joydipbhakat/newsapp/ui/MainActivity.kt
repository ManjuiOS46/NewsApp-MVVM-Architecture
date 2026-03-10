package com.joydipbhakat.newsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.joydipbhakat.newsapp.databinding.ActivityMainBinding
import com.joydipbhakat.newsapp.ui.language.LanguageActivity
import com.joydipbhakat.newsapp.ui.countries.CountriesActivity
import com.joydipbhakat.newsapp.ui.newssources.NewsSourcesActivity
import com.joydipbhakat.newsapp.ui.search.SearchActivity
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.topHeadlines.setOnClickListener {
            startActivity(Intent(this, TopHeadlineActivity::class.java))
        }

        binding.newsSources.setOnClickListener {
            startActivity(Intent(this, NewsSourcesActivity::class.java))
        }

        binding.countries.setOnClickListener {
            startActivity(Intent(this, CountriesActivity::class.java))
        }

        binding.languages.setOnClickListener {
            startActivity(Intent(this, LanguageActivity::class.java))
        }

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }
}