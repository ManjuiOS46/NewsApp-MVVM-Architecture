package com.joydipbhakat.newsapp.ui.newslist

import UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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

    fun getNewsBasedOnLanguage(firstLanguage: String, secondLanguage: String) {
        viewModelScope.launch {
            _uiStateForLanguage.value = UIState.Loading
            topHeadlineRepository.getLanguageNews(firstLanguage)
                .zip(topHeadlineRepository.getLanguageNews(secondLanguage))
                { resultFromFirst, resultFromSecond ->
                    val allLanguagesFromAPI = mutableListOf<Articles>()
                    allLanguagesFromAPI.addAll(resultFromFirst)
                    allLanguagesFromAPI.addAll(resultFromSecond)
                    return@zip allLanguagesFromAPI
                }
                .catch { e ->
                    _uiStateForLanguage.value = UIState.Error(e.toString())
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _uiStateForLanguage.value = UIState.Success(it)
                }
        }
    }
}
