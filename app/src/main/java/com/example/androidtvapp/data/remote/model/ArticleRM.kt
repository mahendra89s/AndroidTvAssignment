package com.example.androidtvapp.data.remote.model

data class ArticleRM(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceRM?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)