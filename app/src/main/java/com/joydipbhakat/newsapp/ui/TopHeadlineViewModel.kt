package com.joydipbhakat.newsapp.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlineViewModel @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {

    private var _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    init {
        fetchNews()
    }


    private fun fetchNews() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                topHeadlineRepository.getTopHeadline("us")
                    .collect { articles ->
                        _uiState.value = UIState.Success(articles)
                    }
            } catch (e: Exception) {
                _uiState.value = UIState.Error("Error occurred due to some reason")
            }
        }
    }

}