package com.joydipbhakat.newsapp.di.component

import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.module.CountriesActivityModule
import com.joydipbhakat.newsapp.ui.countries.CountriesActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [CountriesActivityModule::class])
interface CountriesComponent {
    fun inject(activity: CountriesActivity)
}