package com.joydipbhakat.newsapp.data.network.api

import com.joydipbhakat.newsapp.data.network.models.NewsSourcesResponse
import com.joydipbhakat.newsapp.data.network.models.TopHeadlineResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface NetworkService {

    @GET("top-headlines")
    suspend fun getTopHeadline(
        @Query("country") country: String
    ): TopHeadlineResponse


    @GET("top-headlines/sources")
    suspend fun getNewsSources(): NewsSourcesResponse

    @GET("top-headlines")
    suspend fun getLanguage(@Query("language") language: String): TopHeadlineResponse

    @GET("everything")
    suspend fun getNewsSearch(@Query("q") q: String): TopHeadlineResponse
}