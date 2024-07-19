package com.example.androidtvapp.presentation.mainactivity.model

import com.example.movieapp.presentation.base.ViewEvent

sealed class MainViewEvent : ViewEvent {
    data object OnScreenLoad : MainViewEvent()
    data class OnCategorySelected(
        val category: String
    ) : MainViewEvent()
    data class OnCountrySelected(
        val country: String
    ) : MainViewEvent()

}