package com.joydipbhakat.newsapp.ui.offlinearticles

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import com.joydipbhakat.newsapp.ui.UIState
import com.joydipbhakat.newsapp.utils.DispatcherProvider
import com.joydipbhakat.newsapp.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineArticlesViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private var _uiState = MutableStateFlow<UIState<List<Article>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Article>>> = _uiState

    init {
        if (networkHelper.isOnline())
            fetchArticles()
        else
            fetchArticlesFromDB()
    }

     fun fetchArticles() {
        viewModelScope.launch(dispatcherProvider.dispatcherMain) {
            topHeadlineRepository.getArticles("us").flowOn(dispatcherProvider.dispatcherIO).catch {
                Log.i("Hi","Error is coming")
                _uiState.value = UIState.Error("Error occurred due to some reason")
            }.collect {
                _uiState.value = UIState.Success(it)
            }
        }
    }

    private fun fetchArticlesFromDB() {
        viewModelScope.launch(dispatcherProvider.dispatcherMain) {
            topHeadlineRepository.getArticlesDirectlyFromDB()
                .flowOn(dispatcherProvider.dispatcherIO).catch {
                _uiState.value = UIState.Error("Error occurred due to some reason")
            }.collect {
                _uiState.value = UIState.Success(it)
            }
        }
    }

}