package com.joydipbhakat.newsapp.data.repository

import android.content.Context
import com.google.gson.Gson
import com.joydipbhakat.newsapp.data.local.DatabaseService
import com.joydipbhakat.newsapp.data.network.api.NetworkService
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.network.models.ApiSource
import com.joydipbhakat.newsapp.data.network.models.TopHeadlineResponse
import com.joydipbhakat.newsapp.utils.AppUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TopHeadlineRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    @Mock
    private lateinit var databaseService: DatabaseService

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var gson: Gson

    private lateinit var repository: TopHeadlineRepository

    @Before
    fun setup() {
        repository = TopHeadlineRepository(networkService,databaseService,context,gson)
    }

    @Test
    fun `getTopHeadline returns articles on successful response`() = runTest {
        val articles = listOf(createDummyArticle())

        val response = TopHeadlineResponse(
            status = "ok",
            totalResults = articles.size,
            articles = articles
        )

        Mockito.doReturn(response).`when`(networkService).getTopHeadline(AppUtils.COUNTRY)

        val result = repository.getTopHeadline(AppUtils.COUNTRY).first()

        assertEquals(articles, result)
    }

    @Test
    fun `getTopHeadline returns empty list on network error`() = runTest {
        Mockito.doThrow(RuntimeException("Network error"))
            .`when`(networkService).getTopHeadline(AppUtils.COUNTRY)

        val result = repository.getTopHeadline(AppUtils.COUNTRY).first()

        assertEquals(emptyList<ApiArticles>(), result)
    }
}

private fun createDummyArticle() = ApiArticles(
    apiSource = ApiSource(id = "1", name = "Test Source"),
    title = "Test Title",
    description = "Test Description",
    url = "https://example.com",
    urlToImage = "https://example.com/image.jpg"
)