package com.joydipbhakat.newsapp.di.component

import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.module.NewsListActivityModule
import com.joydipbhakat.newsapp.ui.NewsListActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [NewsListActivityModule::class])
interface NewsListComponent {

    fun inject(newsListActivity: NewsListActivity)
}