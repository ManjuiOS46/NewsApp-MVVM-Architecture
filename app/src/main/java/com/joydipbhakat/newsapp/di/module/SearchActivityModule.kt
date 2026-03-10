package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.ui.search.SearchActivity
import dagger.Module
import dagger.Provides


@Module
class SearchActivityModule(private val searchActivity: SearchActivity) {

    @Provides
    fun provideContext(): Context {
        return searchActivity
    }
}