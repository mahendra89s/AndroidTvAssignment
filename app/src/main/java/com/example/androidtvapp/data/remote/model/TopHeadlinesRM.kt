package com.example.androidtvapp.data.remote.model

data class TopHeadlinesRM(
    val articles: List<ArticleRM>?,
    val status: String?,
    val totalResults: Int?,
    val code: String?,
    val message: String?,
)