package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.ui.newslist.NewsListActivity
import dagger.Module
import dagger.Provides


@Module
class NewsListActivityModule(val activity: NewsListActivity) {

    @Provides
    fun provideContext(): Context {
        return activity
    }
}