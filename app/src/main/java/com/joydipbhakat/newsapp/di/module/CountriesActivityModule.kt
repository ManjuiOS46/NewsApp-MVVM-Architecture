package com.joydipbhakat.newsapp.di.module

import com.joydipbhakat.newsapp.ui.countries.CountriesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
class CountriesActivityModule {
    @Provides
    fun provideAdapter(): CountriesAdapter {
        return CountriesAdapter()
    }
}