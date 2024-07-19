package com.example.androidtvapp.data.remote.datasource

import com.example.androidtvapp.data.remote.model.TopHeadlinesRM
import com.example.androidtvapp.data.remote.services.ApiServices
import com.example.androidtvapp.utils.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    retrofit: Retrofit
) : NewsDataSource {

    private val apiService by lazy { retrofit.create(ApiServices::class.java) }

    override suspend fun fetchTopHeadlines(category: String?,country:String?): DataResult<TopHeadlinesRM> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.fetchTopHeadlines(category,country)
                if (response.isSuccessful) {
                    DataResult.Success(response.body()!!)
                } else {
                    DataResult.Error(response.body()?.message ?: "Something went wrong")
                }
            } catch (exception: Exception) {
                DataResult.Error(
                    exception.message ?: ""
                )
            }
        }
    }
}