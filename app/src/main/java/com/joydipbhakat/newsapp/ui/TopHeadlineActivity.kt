package com.joydipbhakat.newsapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.R
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.component.DaggerTopHeadlineComponent
import com.joydipbhakat.newsapp.di.component.TopHeadlineComponent
import com.joydipbhakat.newsapp.di.module.TopHeadlineActivityModule
import javax.inject.Inject

class TopHeadlineActivity : AppCompatActivity() {

    @ActivityScope
    private lateinit var topHeadlineComponent: TopHeadlineComponent

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: TopHeadlineViewModelFactory

    private lateinit var topHeadlineViewModel: TopHeadlineViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        setContentView(R.layout.activity_main)
        topHeadlineViewModel =
            ViewModelProvider(this, viewModelFactory)[TopHeadlineViewModel::class.java]
    }

    private fun inject() {
        val appComponent =
            (application as NewsApplication).applicationComponent
        topHeadlineComponent = DaggerTopHeadlineComponent.builder()
            .applicationComponent(appComponent)
            .topHeadlineActivityModule(TopHeadlineActivityModule(this)
        ).build()
        topHeadlineComponent.inject(this)
    }
}