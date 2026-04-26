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

    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Before
    fun setup() {
        topHeadlineRepository = TopHeadlineRepository(networkService)
    }

    @Test
    fun getTopHeadline_whenResponse200_shouldReturnListOfArticles() {
        runTest {
            val source = Source(
                id = "id",
                name = "name"
            )
            val article = Articles(
                source = source,
                title = "title",
                description = "description",
                url = "url",
                urlToImage = "urlToImage"
            )

            val articles = mutableListOf<Articles>()
            articles.add(article)

            val response = TopHeadlineResponse(
                status = "ok",
                totalResults = 1,
                articles = articles
            )

            Mockito.doReturn(response).`when`(networkService).getTopHeadline(AppUtils.COUNTRY)
            val actual = topHeadlineRepository.getTopHeadline(AppUtils.COUNTRY).first()
            assertEquals(response.articles, actual)
        }
    }

    @Test
    fun getTopHeadline_whenError_shouldThrowException() {
        runTest {
            Mockito.`when`(networkService.getTopHeadline(AppUtils.COUNTRY))
                .thenThrow(RuntimeException("Error"))
            val actual = topHeadlineRepository.getTopHeadline(AppUtils.COUNTRY).first()
            println(actual)
            assertEquals(emptyList<Articles>(), actual)
        }
    }
}