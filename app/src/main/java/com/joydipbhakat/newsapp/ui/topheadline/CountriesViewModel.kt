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


class CountriesViewModel @Inject constructor(var topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {

    private var _uiState = MutableStateFlow<UIState<List<Articles>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Articles>>> = _uiState

    fun getTopHeadlinesBasedOnCountry(code: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                topHeadlineRepository.getTopHeadline(code).collect {
                    _uiState.value = UIState.Success(it)
                }
            } catch (e: java.lang.Exception) {
                _uiState.value = UIState.Error("Error occurred while obtaining required data")
            }
        }
    }
}
