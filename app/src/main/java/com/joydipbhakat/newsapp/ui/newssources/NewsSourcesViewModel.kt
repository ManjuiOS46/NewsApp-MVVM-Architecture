package com.joydipbhakat.newsapp.ui.newssources

import com.joydipbhakat.newsapp.ui.UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.network.models.NewsSources
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSourcesViewModel @Inject constructor(private var topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {


    private var _uiState = MutableStateFlow<UIState<List<NewsSources>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<NewsSources>>> = _uiState

    init {
        fetchNewsSources()
    }

    fun fetchNewsSources() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                topHeadlineRepository.getNewsSources().collect { newsSources ->
                    _uiState.value = UIState.Success(newsSources)
                }
            } catch (e: java.lang.Exception) {
                _uiState.value = UIState.Error("Error occurred while obtaining news sources")
            }

        }
    }


}