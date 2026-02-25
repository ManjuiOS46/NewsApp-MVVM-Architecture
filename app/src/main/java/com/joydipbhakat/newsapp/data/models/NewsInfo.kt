package com.joydipbhakat.newsapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsInfo(
    val source:String,
    val name:String,
    val code:String
):Parcelable
