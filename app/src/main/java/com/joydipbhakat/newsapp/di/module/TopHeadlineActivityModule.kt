package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineActivity
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineAdapter
import dagger.Module
import dagger.Provides


@Module
class TopHeadlineActivityModule(private val activity: TopHeadlineActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideTopHeadLineAdapter(): TopHeadlineAdapter {
        return TopHeadlineAdapter()
    }
}