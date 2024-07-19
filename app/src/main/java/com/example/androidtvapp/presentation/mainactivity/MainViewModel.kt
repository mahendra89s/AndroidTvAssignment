package com.example.androidtvapp.presentation.mainactivity

import androidx.lifecycle.viewModelScope
import com.example.androidtvapp.domain.usecase.FetchTopHeadlinesUseCase
import com.example.androidtvapp.presentation.base.BaseViewModel
import com.example.androidtvapp.presentation.mainactivity.model.MainUi
import com.example.androidtvapp.presentation.mainactivity.model.MainViewEvent
import com.example.androidtvapp.presentation.mainactivity.model.MainViewState
import com.example.androidtvapp.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTopHeadlinesUseCase: FetchTopHeadlinesUseCase
) : BaseViewModel<MainViewEvent, MainViewState>() {

    override fun handleEvent(event: MainViewEvent) {
        when (event) {
            MainViewEvent.OnScreenLoad -> {
                getTopHeadlines()
            }

            is MainViewEvent.OnCategorySelected -> {
                if(viewState.value.mainUi is MainUi.Success){
                    getTopHeadlines(
                        category = event.category,
                        country = (viewState.value.mainUi as MainUi.Success).selectedCountry
                    )
                }

            }
            is MainViewEvent.OnCountrySelected -> {
                if(viewState.value.mainUi is MainUi.Success){
                    getTopHeadlines(
                        country = event.country,
                    )
                }
            }
        }
    }

    private fun getTopHeadlines(
        category: String? = null,
        country: String? = "us"
    ) {
        viewModelScope.launch {
            setState {
                copy(
                    mainUi = MainUi.Loading,
                )
            }
            fetchTopHeadlinesUseCase.invoke(category, country).collect {
                when (it) {
                    is DataResult.Success -> {
                        setState {
                            copy(
                                mainUi = MainUi.Success(
                                    article = it.data.articles,
                                    categories = getCategories(),
                                    countries = getCountry(),
                                    selectedCountry = country ?: ""
                                )
                            )
                        }
                    }

                    is DataResult.Error -> {
                        setState {
                            copy(
                                mainUi = MainUi.Error(it.message),
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCategories(): List<String> {
        return listOf(
            "general",
            "business",
            "entertainment",
            "health",
            "science",
            "sports",
            "technology"
        )
    }

    private fun getCountry(): List<String> {
        return listOf(
            "ae",
            "ar",
            "at",
            "au",
            "be",
            "bg",
            "br",
            "ca",
            "ch",
            "cn",
            "co",
            "cu",
            "cz",
            "de",
            "eg",
            "fr",
            "gb",
            "gr",
            "hk",
            "hu",
            "id",
            "ie",
            "il",
            "in",
            "it",
            "jp",
            "kr",
            "lt",
            "lv",
            "ma",
            "mx",
            "my",
            "ng",
            "nl",
            "no",
            "nz",
            "ph",
            "pl",
            "pt",
            "ro",
            "rs",
            "ru",
            "sa",
            "se",
            "sg",
            "si",
            "sk",
            "th",
            "tr",
            "tw",
            "ua",
            "us",
            "ve",
            "za"
        )
    }

    override fun getInitialViewState(): MainViewState = MainViewState()

}