package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.ui.CountriesActivity
import com.joydipbhakat.newsapp.ui.CountriesAdapter
import dagger.Module
import dagger.Provides

@Module
class CountriesActivityModule(private val countriesActivity: CountriesActivity) {

    @Provides
    @ActivityContext
    fun providesContext():Context {
        return countriesActivity
    }

    @Provides
    fun provideAdapter():CountriesAdapter {
        return CountriesAdapter()
    }
}