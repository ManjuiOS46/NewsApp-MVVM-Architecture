package com.joydipbhakat.newsapp.data.local.entity

import androidx.room.ColumnInfo

data class Source(
    @ColumnInfo(name = "id")
    var id: String? = null,
    @ColumnInfo(name = "name")
    var name: String? = null
)
