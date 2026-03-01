package com.joydipbhakat.newsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import com.joydipbhakat.newsapp.ui.newslist.NewsListViewModel
import com.joydipbhakat.newsapp.ui.newssources.NewsSourcesViewModel
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineViewModel
import javax.inject.Inject


class ViewModelFactory @Inject constructor(private val topHeadlineRepository: TopHeadlineRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TopHeadlineViewModel::class.java)) {
            return TopHeadlineViewModel(topHeadlineRepository) as T
        }

        if (modelClass.isAssignableFrom(NewsSourcesViewModel::class.java)) {
            return NewsSourcesViewModel(topHeadlineRepository) as T
        }

        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            return NewsListViewModel(topHeadlineRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }



}
