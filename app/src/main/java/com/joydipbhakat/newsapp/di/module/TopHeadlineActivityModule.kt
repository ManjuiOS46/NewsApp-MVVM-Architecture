package com.joydipbhakat.newsapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class TopHeadlineActivityModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }
}