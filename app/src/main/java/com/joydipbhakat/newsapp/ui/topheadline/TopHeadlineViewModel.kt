package com.joydipbhakat.newsapp.ui.topheadline


import UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {

    private var _uiState = MutableStateFlow<UIState<List<Articles>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Articles>>> = _uiState

    init {
        fetchNews()
    }


     fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                topHeadlineRepository.getTopHeadline("US")
                    .collect { articles ->
                        _uiState.value = UIState.Success(articles)
                    }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Error occurred due to some reason")
            }
        }
    }

}