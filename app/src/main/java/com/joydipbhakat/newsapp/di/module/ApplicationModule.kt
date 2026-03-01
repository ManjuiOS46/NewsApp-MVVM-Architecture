package com.joydipbhakat.newsapp.di.module

import android.content.Context
import com.google.gson.Gson
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.data.api.NetworkService
import com.joydipbhakat.newsapp.di.ApplicationContext
import com.joydipbhakat.newsapp.di.BaseUrl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: NewsApplication) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }

    @Provides
    @BaseUrl
    fun provideBaseUrl():String
    {
        return "https://newsapi.org/v2/"
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideNetworkService(
           okHttpClient: OkHttpClient,
           @BaseUrl baseUrl: String,
           gson: Gson
       ): NetworkService =
           Retrofit.Builder()
               .client(okHttpClient)
               .baseUrl(baseUrl)
               .addConverterFactory(GsonConverterFactory.create(gson))
               .build().create(NetworkService::class.java)

}