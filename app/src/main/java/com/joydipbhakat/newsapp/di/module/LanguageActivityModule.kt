package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.ui.language.LanguageActivity
import com.joydipbhakat.newsapp.ui.language.LanguageAdapter
import dagger.Module
import dagger.Provides

@Module
class LanguageActivityModule(val languageActivity: LanguageActivity) {

    @Provides
    @ActivityContext
    fun provideContext():Context{
        return languageActivity
    }

    @Provides
    fun provideAdapter(): LanguageAdapter {
        return LanguageAdapter()
    }


}