package com.joydipbhakat.newsapp.data.local

import com.joydipbhakat.newsapp.data.local.entity.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppDatabaseService constructor(private val appDatabase: AppDatabase):DatabaseService {
    override fun getArticles(): Flow<List<Article>> {
        return flow{emit(appDatabase.articleDao().getAllArticle())}
    }

    override fun clearAndInsert(article: List<Article>) {
        appDatabase.articleDao().clearAndInsert(article)
    }
}