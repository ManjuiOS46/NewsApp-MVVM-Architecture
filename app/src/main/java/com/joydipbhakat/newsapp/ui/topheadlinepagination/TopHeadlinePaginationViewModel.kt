package com.joydipbhakat.newsapp.ui.topheadlinepagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.joydipbhakat.newsapp.data.local.entity.Article
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TopHeadlinePaginationViewModel @Inject constructor(
    val topHeadlineRepository: TopHeadlineRepository
) : ViewModel() {

    fun getArticlesFromPagination(): Flow<PagingData<Article>> {
        return topHeadlineRepository.getArticlesFromPagination().cachedIn(viewModelScope)
    }

}