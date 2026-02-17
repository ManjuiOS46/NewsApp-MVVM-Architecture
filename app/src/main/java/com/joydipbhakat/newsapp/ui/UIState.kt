package com.joydipbhakat.newsapp.ui

import com.joydipbhakat.newsapp.data.models.Articles

sealed class UIState {
    data class Success(val list: List<Articles>) : UIState()
    data class Error(val message: String) : UIState()
    object Loading : UIState()

}
