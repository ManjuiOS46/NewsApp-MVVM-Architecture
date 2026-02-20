package com.joydipbhakat.newsapp.di.component

import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.module.NewsSourcesActivityModule
import com.joydipbhakat.newsapp.ui.NewsSourcesActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [NewsSourcesActivityModule::class]
)
interface NewsSourcesComponent {
    fun inject(activity: NewsSourcesActivity)
}