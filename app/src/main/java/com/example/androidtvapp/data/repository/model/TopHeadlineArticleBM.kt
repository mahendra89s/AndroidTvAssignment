package com.example.androidtvapp.data.repository.model

import com.example.androidtvapp.data.remote.model.SourceRM

data class TopHeadlineArticleBM(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val sourceRM: SourceRM?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)
