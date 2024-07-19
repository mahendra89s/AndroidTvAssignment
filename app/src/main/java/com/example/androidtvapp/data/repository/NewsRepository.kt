package com.example.androidtvapp.data.repository

import com.example.androidtvapp.data.repository.model.TopHeadlineArticleListBM
import com.example.androidtvapp.utils.DataResult

interface NewsRepository {
    suspend fun fetchTopNews(category: String? = null, country: String? = null) : DataResult<TopHeadlineArticleListBM>
}