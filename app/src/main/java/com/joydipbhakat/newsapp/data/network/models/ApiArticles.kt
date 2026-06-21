package com.joydipbhakat.newsapp.data.network.models

import com.google.gson.annotations.SerializedName
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.local.entity.Source

data class ApiArticles(
    @SerializedName("source")
    var apiSource: ApiSource? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("url")
    var url: String? = null ,
    @SerializedName("urlToImage")
    var urlToImage: String? = null
)

fun ApiArticles.toArticleEntity(): Article {
    return Article(
        source = apiSource?.toSourceEntity() ?: Source("",""),
        title = title ?: "",
        description = description ?: "",
        url = url ?:"",
        urlToImage = urlToImage ?: ""
    )
}
