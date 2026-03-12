package com.joydipbhakat.newsapp

import com.joydipbhakat.newsapp.utils.AppUtils
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder().header(
            "X-Api-Key",AppUtils.API_KEY
        ).header("User-Agent","ABC")
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}