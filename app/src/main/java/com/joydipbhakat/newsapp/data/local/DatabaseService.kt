package com.joydipbhakat.newsapp.data.local

import androidx.paging.PagingSource
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.local.entity.RemoteKeys
import kotlinx.coroutines.flow.Flow


interface DatabaseService {

    suspend fun clearArticles()

    suspend fun insertArticles(
        articles: List<Article>
    )

    suspend fun clearRemoteKeys()

    suspend fun insertRemoteKeys(
        remoteKeys: List<RemoteKeys>
    )

    suspend fun getRemoteKey(
        articleId: String
    ): RemoteKeys?

    fun pagingSource(): PagingSource<Int, Article>

    suspend fun withTransaction(
        block: suspend () -> Unit
    )
    fun getArticles(): Flow<List<Article>>
    fun clearAndInsert(article : List<Article>)
}