package com.joydipbhakat.newsapp.ui.newslist

import UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsListViewModel @Inject constructor(var topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {

    private var _uiStateForCountry = MutableStateFlow<UIState<List<Articles>>>(UIState.Loading)
    val uiStateForCountry: StateFlow<UIState<List<Articles>>> = _uiStateForCountry

    private var _uiStateForLanguage = MutableStateFlow<UIState<List<Articles>>>(UIState.Loading)
    val uiStateForLanguage: StateFlow<UIState<List<Articles>>> = _uiStateForLanguage

    fun getTopHeadlinesBasedOnCountry(code: String) {
        viewModelScope.launch {
            _uiStateForCountry.value = UIState.Loading
            try {
                topHeadlineRepository.getTopHeadline(code).collect {
                    _uiStateForCountry.value = UIState.Success(it)
                }
            } catch (e: java.lang.Exception) {
                _uiStateForCountry.value = UIState.Error("Error occurred while obtaining required data")
            }
        }
    }

    fun getNewsBasedOnLanguage(code: String) {
        viewModelScope.launch {
            _uiStateForLanguage.value = UIState.Loading
            try {
                topHeadlineRepository.getLanguageNews(code).collect {
                    _uiStateForLanguage.value = UIState.Success(it)
                }
            } catch (e: Exception) {
                _uiStateForLanguage.value =
                    UIState.Error("Error occurred while obtaining for language")
            }
        }
    }
}
