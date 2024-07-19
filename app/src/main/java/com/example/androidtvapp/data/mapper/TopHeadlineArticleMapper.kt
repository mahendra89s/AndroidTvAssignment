package com.example.androidtvapp.data.mapper

import com.example.androidtvapp.data.remote.model.TopHeadlinesRM
import com.example.androidtvapp.data.repository.model.TopHeadlineArticleBM
import com.example.androidtvapp.data.repository.model.TopHeadlineArticleListBM

class TopHeadlineArticleMapper {
    fun remoteToBusinessModel(model: TopHeadlinesRM): TopHeadlineArticleListBM {
        return TopHeadlineArticleListBM(
            articles = model.articles?.map {
                TopHeadlineArticleBM(
                    author = it.author ?: "",
                    content = it.content ?: "",
                    description = it.description ?: "",
                    publishedAt = it.publishedAt ?: "",
                    sourceRM = it.source!!,
                    title = it.title ?: "",
                    url = it.url ?: "",
                    urlToImage = it.urlToImage ?: ""
                )
            } ?: emptyList()
        )
    }
}