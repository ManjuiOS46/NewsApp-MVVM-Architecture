package com.joydipbhakat.newsapp.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {

    private var _topHeadlineNews = MutableStateFlow<List<Articles>>(emptyList())
    val topHeadlineNews = _topHeadlineNews.asStateFlow()

    init {
        fetchNews()
    }


    private fun fetchNews() {
        viewModelScope.launch {
            topHeadlineRepository.getTopHeadline("us").collect { articles ->
                _topHeadlineNews.value = articles
            }
        }
    }
}