package com.example.androidtvapp.domain.usecase

import com.example.androidtvapp.data.remote.model.TopHeadlinesRM
import com.example.androidtvapp.data.repository.NewsRepository
import com.example.androidtvapp.data.repository.model.TopHeadlineArticleListBM
import com.example.androidtvapp.utils.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(
        category: String? = null,
        country: String? = null,
    ): Flow<DataResult<TopHeadlineArticleListBM>> {
        return flow {
            emit(newsRepository.fetchTopNews(category,country))
        }
    }
}