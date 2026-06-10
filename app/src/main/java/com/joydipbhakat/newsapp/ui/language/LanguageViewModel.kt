package com.joydipbhakat.newsapp.ui.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import com.joydipbhakat.newsapp.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(val topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {
    private val _languages =
        MutableStateFlow<List<Pair<String, String>>>(emptyList())

    val languages = _languages.asStateFlow()

    private var _uiStateForLanguage = MutableStateFlow<UIState<List<ApiArticles>>>(UIState.Loading)
    val uiStateForLanguage: StateFlow<UIState<List<ApiArticles>>> = _uiStateForLanguage

    init {
        loadLanguages()
    }

    private fun loadLanguages() {
        viewModelScope.launch(Dispatchers.Main) {
            topHeadlineRepository.getLanguages().flowOn(Dispatchers.IO).collect {
                _languages.value = it
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