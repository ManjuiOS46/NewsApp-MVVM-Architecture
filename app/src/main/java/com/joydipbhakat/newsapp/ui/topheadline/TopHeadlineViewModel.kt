package com.joydipbhakat.newsapp.ui.topheadline


import com.joydipbhakat.newsapp.ui.UIState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import com.joydipbhakat.newsapp.utils.AppUtils
import com.joydipbhakat.newsapp.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider
) :
    ViewModel() {

    private var _uiState = MutableStateFlow<UIState<List<ApiArticles>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<ApiArticles>>> = _uiState

    init {
        fetchNews()
    }


    fun fetchNews() {
        viewModelScope.launch(dispatcherProvider.dispatcherMain) {
            topHeadlineRepository.getTopHeadline(AppUtils.COUNTRY)
                .flowOn(dispatcherProvider.dispatcherIO)
                .catch {
                    _uiState.value = UIState.Error("Error occurred due to some reason")
                }
                .collect { articles ->
                    _uiState.value = UIState.Success(articles)
                }
        }
    }

}