package com.example.androidtvapp.data.repository

import com.example.androidtvapp.data.mapper.TopHeadlineArticleMapper
import com.example.androidtvapp.data.remote.datasource.NewsDataSource
import com.example.androidtvapp.data.repository.model.TopHeadlineArticleListBM
import com.example.androidtvapp.utils.DataResult
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val topHeadlineArticleMapper: TopHeadlineArticleMapper
) : NewsRepository {

    override suspend fun fetchTopNews(category: String?,country:String?): DataResult<TopHeadlineArticleListBM> {
        return when(val result = newsDataSource.fetchTopHeadlines(category,country)){
            is DataResult.Success -> {
                DataResult.Success(topHeadlineArticleMapper.remoteToBusinessModel(result.data))
            }
            is DataResult.Error -> {
                result
            }
        }
    }

}