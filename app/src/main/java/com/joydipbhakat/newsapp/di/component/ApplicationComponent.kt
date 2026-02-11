package com.joydipbhakat.newsapp.di.component

import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import com.joydipbhakat.newsapp.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: NewsApplication)
    fun topHeadlineRepository(): TopHeadlineRepository
}