package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.ui.NewsSourcesActivity
import com.joydipbhakat.newsapp.ui.NewsSourcesAdapter
import dagger.Module
import dagger.Provides


@Module
class NewsSourcesActivityModule(private val activity: NewsSourcesActivity) {

    @Provides
    @ActivityContext
    fun providesContext(): Context {
        return activity
    }

    @Provides
    fun provideAdapter():NewsSourcesAdapter{
        return NewsSourcesAdapter()
    }
}