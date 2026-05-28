package com.joydipbhakat.newsapp.data.network.models

import com.google.gson.annotations.SerializedName
import com.joydipbhakat.newsapp.data.local.entity.Source

data class ApiSource(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String = ""
)

fun ApiSource.toSourceEntity(): Source {
    return Source(id, name)
}
