package com.joydipbhakat.newsapp.viewmodel

import app.cash.turbine.test
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.network.models.ApiSource
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import com.joydipbhakat.newsapp.ui.UIState
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineViewModel
import com.joydipbhakat.newsapp.utils.AppUtils.COUNTRY
import com.joydipbhakat.newsapp.utils.TestDispatcherProvider
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {

    @Mock
    private lateinit var topHeadlineRepository: TopHeadlineRepository


    @get:Rule
    private val dispatcherProvider = TestDispatcherProvider()

    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldReturnListArticles() {

        val apiSource = ApiSource(
            id = "id",
            name = "name"
        )
        val apiArticles = ApiArticles(
            apiSource = apiSource,
            title = "title",
            description = "description",
            url = "url",
            urlToImage = "urlToImage"
        )
        runTest {

            doReturn(flowOf(listOf(apiArticles)))
                .`when`(topHeadlineRepository)
                .getTopHeadline(COUNTRY)

            val viewModel = TopHeadlineViewModel(topHeadlineRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(UIState.Success(listOf(apiArticles)), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun fetchNews_whenRepositoryResponseIsError_shouldThrowException() {
        runTest {
            doReturn(flow<List<ApiArticles>> { throw Exception("(Error occurred due to some reason") })
                .`when`(topHeadlineRepository)
                .getTopHeadline(COUNTRY)
            val viewModel = TopHeadlineViewModel(topHeadlineRepository, dispatcherProvider)
            viewModel.uiState.test {
                assertEquals(UIState.Error("Error occurred due to some reason"), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
    }

}