package com.joydipbhakat.newsapp.data.network.models

import com.google.gson.annotations.SerializedName

data class TopHeadlineResponse(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("totalResults")
    var totalResults: Int? = null,
    @SerializedName("articles")
    var articles: List<ApiArticles> = ArrayList()
)
