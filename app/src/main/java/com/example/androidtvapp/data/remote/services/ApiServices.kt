package com.example.androidtvapp.data.remote.services

import com.example.androidtvapp.data.remote.model.TopHeadlinesRM
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("top-headlines")
    suspend fun fetchTopHeadlines(
        @Query("category") category: String?,
        @Query("country") country: String?
    ): Response<TopHeadlinesRM>
}