package com.joydipbhakat.newsapp.data.local

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.local.entity.RemoteKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppDatabaseService constructor(private val appDatabase: AppDatabase):DatabaseService {
    override suspend fun clearArticles() {
        appDatabase.articleDao().deleteAll()
    }

    override suspend fun insertArticles(articles: List<Article>) {
        appDatabase.articleDao().insertAll(articles)
    }

    override suspend fun clearRemoteKeys() {
        appDatabase.remoteKeysDao().clearRemoteKeys()
    }

    override suspend fun insertRemoteKeys(remoteKeys: List<RemoteKeys>) {
       appDatabase.remoteKeysDao().insertAll(remoteKeys)
    }

    override suspend fun getRemoteKey(articleId: String): RemoteKeys? {
        return appDatabase.remoteKeysDao().getRemoteKey(articleId)
    }

    override fun pagingSource(): PagingSource<Int, Article> {
        return appDatabase.articleDao().getAllArticleForPagination()
    }

    override suspend fun withTransaction(block: suspend () -> Unit) {
        appDatabase.withTransaction {
            block()
        }
    }


    override fun getArticles(): Flow<List<Article>> {
        return flow{emit(appDatabase.articleDao().getAllArticle())}
    }

    override fun clearAndInsert(article: List<Article>) {
        appDatabase.articleDao().clearAndInsert(article)
    }
}