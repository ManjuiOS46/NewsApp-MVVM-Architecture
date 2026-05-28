package com.joydipbhakat.newsapp.ui.newslist

import com.joydipbhakat.newsapp.ui.UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(var topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {

    private var _uiStateForCountry = MutableStateFlow<UIState<List<ApiArticles>>>(UIState.Loading)
    val uiStateForCountry: StateFlow<UIState<List<ApiArticles>>> = _uiStateForCountry

    private var _uiStateForLanguage = MutableStateFlow<UIState<List<ApiArticles>>>(UIState.Loading)
    val uiStateForLanguage: StateFlow<UIState<List<ApiArticles>>> = _uiStateForLanguage

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
                    val allLanguagesFromAPI = mutableListOf<ApiArticles>()
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
