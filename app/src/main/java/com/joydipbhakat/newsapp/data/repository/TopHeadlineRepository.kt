package com.joydipbhakat.newsapp.data.repository

import com.joydipbhakat.newsapp.data.api.NetworkService
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.models.NewsSources
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {
    suspend fun getTopHeadline(country: String): Flow<List<Articles>> = flow {
        val articles = try {
            networkService.getTopHeadline(country).articles
        } catch (e: Exception) {
            emptyList()
        }
        emit(articles)
    }

    suspend fun getNewsSources(): Flow<List<NewsSources>> {
        return flow {
            emit(networkService.getNewsSources())
        }.map { it.sources }
    }

    suspend fun getLanguageNews(code:String):Flow<List<Articles>> {
        return flow{
            emit(networkService.getLanguage(code))
        }.map { it.articles }
    }

    suspend fun getNewsSearch(q:String):Flow<List<Articles>> {
        return flow{
            emit(networkService.getNewsSearch(q))
        }.map { it.articles }
    }
}