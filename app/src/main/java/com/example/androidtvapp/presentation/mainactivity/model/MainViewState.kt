package com.example.androidtvapp.presentation.mainactivity.model

import com.example.androidtvapp.data.repository.model.TopHeadlineArticleBM
import com.example.movieapp.presentation.base.ViewState

data class MainViewState(
    val mainUi: MainUi = MainUi.None,

) : ViewState


sealed class MainUi{
    data object None : MainUi()
    data object Loading : MainUi()
    data class Success(
        val article: List<TopHeadlineArticleBM>,
        val categories: List<String> = emptyList(),
        val selectedCountry: String = "us",
        val countries: List<String> = emptyList(),
    ) : MainUi()
    data class Error(
        val message: String
    ) : MainUi()
}