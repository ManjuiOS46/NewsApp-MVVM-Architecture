package com.joydipbhakat.newsapp.data.api

import com.joydipbhakat.newsapp.data.models.NewsSourcesResponse
import com.joydipbhakat.newsapp.data.models.TopHeadlineResponse
import com.joydipbhakat.newsapp.utils.AppUtils
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton


@Singleton
interface NetworkService {
    @Headers("X-Api-Key: ${AppUtils.API_KEY}", "User-Agent:ABC")
    @GET("top-headlines")
    suspend fun getTopHeadline(
        @Query("country") country: String
    ): TopHeadlineResponse

    @Headers("X-Api-Key: ${AppUtils.API_KEY}", "User-Agent:ABC")
    @GET("top-headlines/sources")
    suspend fun getNewsSources():NewsSourcesResponse
}