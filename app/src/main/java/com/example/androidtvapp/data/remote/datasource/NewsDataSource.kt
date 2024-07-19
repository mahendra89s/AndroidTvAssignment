package com.example.androidtvapp.data.remote.datasource

import com.example.androidtvapp.data.remote.model.TopHeadlinesRM
import com.example.androidtvapp.utils.DataResult

interface NewsDataSource {
    suspend fun fetchTopHeadlines(category: String?=null, country: String?=null) : DataResult<TopHeadlinesRM>
}