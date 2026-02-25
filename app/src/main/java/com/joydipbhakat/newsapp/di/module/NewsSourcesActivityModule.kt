package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.ui.newssources.NewsSourcesActivity
import com.joydipbhakat.newsapp.ui.newssources.NewsSourcesAdapter
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
    fun provideAdapter(): NewsSourcesAdapter {
        return NewsSourcesAdapter()
    }
}