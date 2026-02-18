package com.joydipbhakat.newsapp.di.component


import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.module.TopHeadlineActivityModule
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class],modules = [TopHeadlineActivityModule::class])
interface TopHeadlineComponent {
    fun inject(activity: TopHeadlineActivity)
}