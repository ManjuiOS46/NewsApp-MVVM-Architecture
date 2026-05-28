package com.joydipbhakat.newsapp.data.network.models

import com.google.gson.annotations.SerializedName
import com.joydipbhakat.newsapp.data.local.entity.Article

data class ApiArticles(
    @SerializedName("source")
    var apiSource: ApiSource = ApiSource(),
    @SerializedName("title")
    var title: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("url")
    var url: String = "" ,
    @SerializedName("urlToImage")
    var urlToImage: String = ""
)

fun ApiArticles.toArticleEntity(): Article {
    return Article(
        source = apiSource.toSourceEntity(),
        title = title ?: "",
        description = description ?: "",
        url = url ?:"",
        urlToImage = urlToImage ?: ""
    )
}
