package com.joydipbhakat.newsapp.ui.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joydipbhakat.newsapp.data.network.models.ApiArticles
import com.joydipbhakat.newsapp.data.network.models.Countries
import com.joydipbhakat.newsapp.data.repository.TopHeadlineRepository
import com.joydipbhakat.newsapp.ui.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(val topHeadlineRepository: TopHeadlineRepository) :
    ViewModel() {
    private val _countries =
        MutableStateFlow<List<Countries>>(emptyList())

    val countries = _countries.asStateFlow()

    private var _uiStateForCountry = MutableStateFlow<UIState<List<ApiArticles>>>(UIState.Loading)
    val uiStateForCountry: StateFlow<UIState<List<ApiArticles>>> = _uiStateForCountry

    private val _selectedCountry = MutableStateFlow<String?>(null)
    val selectedCountry = _selectedCountry.asStateFlow()

    init {
        loadCountries()
    }

    private fun loadCountries() {
        viewModelScope.launch(Dispatchers.Main) {
            topHeadlineRepository.getCountries().flowOn(Dispatchers.IO).collect {
                _countries.value = it
            }
        }
    }

    fun onCountrySelected(code: String) {
        _selectedCountry.value = code
        getTopHeadlinesBasedOnCountry(code)
    }

    fun clearSelectedCountry() {
        _selectedCountry.value = null
        _uiStateForCountry.value = UIState.Loading
    }

    fun getTopHeadlinesBasedOnCountry(code: String) {
        viewModelScope.launch {
            _uiStateForCountry.value = UIState.Loading
            try {
                topHeadlineRepository.getTopHeadline(code).collect {
                    _uiStateForCountry.value = UIState.Success(it)
                }
            } catch (e: java.lang.Exception) {
                _uiStateForCountry.value =
                    UIState.Error("Error occurred while obtaining required data")
            }
        }
    }
}