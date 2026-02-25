package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Module
import dagger.Provides


@Module
class TopHeadlineActivityModule(private val activity: TopHeadlineActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context {
        return activity
    }


}