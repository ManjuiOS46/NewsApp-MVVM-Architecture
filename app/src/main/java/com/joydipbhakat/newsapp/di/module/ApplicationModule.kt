package com.joydipbhakat.newsapp.di.module

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.joydipbhakat.newsapp.data.network.api.NetworkService
import com.joydipbhakat.newsapp.di.BaseUrl
import com.joydipbhakat.newsapp.data.network.api.ApiKeyInterceptor
import com.joydipbhakat.newsapp.data.local.AppDatabase
import com.joydipbhakat.newsapp.data.local.AppDatabaseService
import com.joydipbhakat.newsapp.data.local.DatabaseService
import com.joydipbhakat.newsapp.di.DatabaseName
import com.joydipbhakat.newsapp.utils.DefaultDispatcher
import com.joydipbhakat.newsapp.utils.DefaultNetworkHelper
import com.joydipbhakat.newsapp.utils.DispatcherProvider
import com.joydipbhakat.newsapp.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String {
        return "https://newsapi.org/v2/"
    }

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
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

    @Provides
    @Singleton
    fun provideDefaultDispatcher(): DispatcherProvider = DefaultDispatcher()


    @Provides
    @DatabaseName
    fun provideDatabaseName(): String = "news-database"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, databaseName
    ).build()

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: AppDatabase): DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper =
        DefaultNetworkHelper(context)

}