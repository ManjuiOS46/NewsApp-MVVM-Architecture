package com.joydipbhakat.newsapp.data.models

import com.google.gson.annotations.SerializedName

data class Articles(
    @SerializedName("source")
    var source: Source? = Source(),
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("url")
    var url: String? = null,
    @SerializedName("urlToImage")
    var urlToImage: String? = null
)
