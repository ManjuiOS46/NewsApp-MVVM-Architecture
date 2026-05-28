package com.joydipbhakat.newsapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "article_id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    var title: String? = "",
    @ColumnInfo(name = "description")
    var description: String? = "",
    @ColumnInfo(name = "url")
    var url: String? = "",
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = "",
    @Embedded var source: Source
)
