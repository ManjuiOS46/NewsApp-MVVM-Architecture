package com.joydipbhakat.newsapp.data.network.models

import com.google.gson.annotations.SerializedName

data class NewsSourcesResponse(
    @SerializedName("status")
    val status: String? = null,

    @SerializedName("sources")
    val sources: List<NewsSources> = ArrayList()
)
