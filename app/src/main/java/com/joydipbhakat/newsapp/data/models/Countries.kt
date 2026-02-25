package com.joydipbhakat.newsapp.data.models

import com.google.gson.annotations.SerializedName

data class Countries(
    val name: String,
    @SerializedName("alpha-2")
    val code: String
)
