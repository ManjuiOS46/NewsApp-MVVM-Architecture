package com.joydipbhakat.newsapp.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.joydipbhakat.newsapp.data.local.DatabaseService
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.network.api.NetworkService
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.network.models.Countries
import com.joydipbhakat.newsapp.data.network.models.NewsSources
import com.joydipbhakat.newsapp.data.network.models.toArticleEntity
import com.joydipbhakat.newsapp.data.pagination.TopHeadlineRemoteMediator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    @ApplicationContext private val context: Context,
    private val gson: Gson
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getArticles(country: String): Flow<List<Article>> {
        return flow {
            val response = networkService.getTopHeadline(country)
            emit(response)
        }.map { response ->
            val articles = response.articles.map {
                it.toArticleEntity()
            }
            articles
        }.flatMapConcat { articles ->
            flow {
                databaseService.clearAndInsert(articles)
                emit(Unit)
            }
        }.flatMapConcat {
            databaseService.getArticles()
        }.catch {

        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getArticlesFromPagination() =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = false
            ),

            remoteMediator = TopHeadlineRemoteMediator(
                networkService = networkService,
                databaseService = databaseService
            ),

            pagingSourceFactory = {
                databaseService.pagingSource()
            }
        ).flow

    fun getArticlesDirectlyFromDB(): Flow<List<Article>> {
        return databaseService.getArticles()
    }

    suspend fun getTopHeadline(country: String): Flow<List<ApiArticles>> = flow {
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

    suspend fun getLanguageNews(code: String): Flow<List<ApiArticles>> {
        return flow {
            emit(networkService.getLanguage(code))
        }.map { it.articles }
    }

    suspend fun getNewsSearch(q: String): Flow<List<ApiArticles>> {
        return flow {
            emit(networkService.getNewsSearch(q))
        }.map { it.articles }
    }

    fun getCountries(): Flow<List<Countries>> = flow {
        emit(loadCountriesFromAssets())
    }

    private fun loadCountriesFromAssets(): List<Countries> {
        val jsonString = context.assets.open("countries.json")
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<List<Countries>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    fun getLanguages() = flow {
        emit(loadLanguagesFromAsset())
    }
    private fun loadLanguagesFromAsset(): List<Pair<String, String>> {
        val jsonString = context.assets.open("language.json").bufferedReader().use { it.readText() }
        val type = object : TypeToken<Map<String, String>>() {}.type
        val languageMap: Map<String, String> = gson.fromJson(jsonString, type)
        return languageMap.toList()
    }
}