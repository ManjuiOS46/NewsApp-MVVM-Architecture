package com.joydipbhakat.newsapp.di.module

import com.joydipbhakat.newsapp.ui.newssources.NewsSourcesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
class NewsSourcesActivityModule {
    @Provides
    fun provideAdapter(): NewsSourcesAdapter {
        return NewsSourcesAdapter()
    }
}