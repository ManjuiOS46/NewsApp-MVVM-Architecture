package com.joydipbhakat.newsapp.data.local

import com.joydipbhakat.newsapp.data.local.entity.Article
import kotlinx.coroutines.flow.Flow


interface DatabaseService {
    fun getArticles(): Flow<List<Article>>
    fun clearAndInsert(article : List<Article>)
}