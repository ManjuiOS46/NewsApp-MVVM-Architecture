package com.joydipbhakat.newsapp.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.joydipbhakat.newsapp.data.local.DatabaseService
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.local.entity.RemoteKeys
import com.joydipbhakat.newsapp.data.network.api.NetworkService
import com.joydipbhakat.newsapp.data.network.models.toArticleEntity

@OptIn(ExperimentalPagingApi::class)
class TopHeadlineRemoteMediator(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) : RemoteMediator<Int, Article>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Article>
    ): MediatorResult {

        try {

            val page = when (loadType) {

                LoadType.REFRESH -> {

                    val remoteKeys =
                        getRemoteKeyClosestToCurrentPosition(state)

                    remoteKeys?.nextKey?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {

                    val remoteKeys =
                        getRemoteKeyForFirstItem(state)

                    val prevKey = remoteKeys?.prevKey

                    prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                }

                LoadType.APPEND -> {

                    val remoteKeys =
                        getRemoteKeyForLastItem(state)

                    val nextKey = remoteKeys?.nextKey

                    nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                }
            }
            val response =
                networkService.getTopHeadlinePagination(page)
            val articles =
                response.articles.map {
                    it.toArticleEntity()
                }
            val endOfPaginationReached =
                articles.isEmpty()

            databaseService.withTransaction {

                if (loadType == LoadType.REFRESH) {

                    databaseService.clearArticles()

                    databaseService.clearRemoteKeys()
                }

                val prevKey =
                    if (page == 1) null else page - 1

                val nextKey =
                    if (endOfPaginationReached)
                        null
                    else
                        page + 1

                val keys =
                    articles.map { article ->
                        RemoteKeys(
                            articleUrl = article.url,
                            prevKey = prevKey,
                            nextKey = nextKey
                        )
                    }
                databaseService.insertRemoteKeys(keys)

                databaseService.insertArticles(articles)
            }

            return MediatorResult.Success(
                endOfPaginationReached =
                endOfPaginationReached
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Article>
    ): RemoteKeys? {

        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let {
                databaseService.getRemoteKey(it.url)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Article>
    ): RemoteKeys? {

        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let {
                databaseService.getRemoteKey(it.url)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Article>
    ): RemoteKeys? {

        return state.anchorPosition
            ?.let { position ->
                state.closestItemToPosition(position)
            }
            ?.let {
                databaseService.getRemoteKey(it.url)
            }
    }
}