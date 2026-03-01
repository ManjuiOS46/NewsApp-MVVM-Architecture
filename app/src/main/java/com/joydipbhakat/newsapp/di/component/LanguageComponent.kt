package com.joydipbhakat.newsapp.di.component

import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.module.LanguageActivityModule
import com.joydipbhakat.newsapp.ui.language.LanguageActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [LanguageActivityModule::class])
interface LanguageComponent {
    fun inject(activity: LanguageActivity)
}