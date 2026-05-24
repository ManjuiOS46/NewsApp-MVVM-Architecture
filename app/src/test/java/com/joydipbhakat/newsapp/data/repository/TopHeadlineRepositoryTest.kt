package com.joydipbhakat.newsapp.data.repository

import com.joydipbhakat.newsapp.data.api.NetworkService
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.models.Source
import com.joydipbhakat.newsapp.data.models.TopHeadlineResponse
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

    private lateinit var repository: TopHeadlineRepository

    @Before
    fun setup() {
        repository = TopHeadlineRepository(networkService)
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

        assertEquals(emptyList<Articles>(), result)
    }
}

// Helper function
private fun createDummyArticle() = Articles(
    source = Source(id = "1", name = "Test Source"),
    title = "Test Title",
    description = "Test Description",
    url = "https://example.com",
    urlToImage = "https://example.com/image.jpg"
)