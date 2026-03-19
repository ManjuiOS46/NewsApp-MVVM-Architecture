package com.joydipbhakat.newsapp.di.module

import com.joydipbhakat.newsapp.ui.language.LanguageAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class LanguageActivityModule {
    @Provides
    fun provideAdapter(): LanguageAdapter {
        return LanguageAdapter()
    }


}