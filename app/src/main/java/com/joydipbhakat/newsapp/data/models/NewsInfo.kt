package com.joydipbhakat.newsapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsInfo(
    val source:String,
    val countryCode:String?,
    val firstLanguage:String?,
    val secondLanguage:String?
):Parcelable
