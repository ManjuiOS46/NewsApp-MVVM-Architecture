package com.joydipbhakat.newsapp.ui.search

import UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UIState<Flow<List<Articles>>>>(UIState.Loading)
    val uiState: StateFlow<UIState<Flow<List<Articles>>>> = _uiState

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query

    init {
        newsSearchNetworkCall()
    }

    fun newsSearch(q: String) {
        _query.value = q
    }

    private fun newsSearchNetworkCall() {
        viewModelScope.launch {
            _query.debounce(500)
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        emit(UIState.Loading)
                        try {
                            val result = topHeadlineRepository.getNewsSearch(query)
                            emit(UIState.Success(result))
                        } catch (e: Exception) {
                            emit(UIState.Error("Not able to find the required search"))
                        }
                    }
                }.collect {
                    _uiState.value = it
                }
        }
    }
}