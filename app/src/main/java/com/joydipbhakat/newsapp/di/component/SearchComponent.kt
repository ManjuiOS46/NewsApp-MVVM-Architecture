package com.joydipbhakat.newsapp.di.component

import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.module.SearchActivityModule
import com.joydipbhakat.newsapp.ui.search.SearchActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [SearchActivityModule::class])
interface SearchComponent {
    fun inject(activity: SearchActivity)
}