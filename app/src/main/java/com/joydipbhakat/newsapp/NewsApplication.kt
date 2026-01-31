package com.joydipbhakat.newsapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.joydipbhakat.newsapp.di.component.ApplicationComponent
import com.joydipbhakat.newsapp.di.component.DaggerApplicationComponent
import com.joydipbhakat.newsapp.di.module.ApplicationModule
import javax.inject.Inject

class NewsApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var context: Context
    override fun onCreate() {
        inject()
        super.onCreate()
    }

    private fun inject() {
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)
        ).build()
        applicationComponent.inject(this)
    }
}