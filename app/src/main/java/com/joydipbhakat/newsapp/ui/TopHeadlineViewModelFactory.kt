package com.joydipbhakat.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import javax.inject.Inject


class TopHeadlineViewModelFactory @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopHeadlineViewModel(topHeadlineRepository) as T
    }


}
