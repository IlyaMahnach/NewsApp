package com.example.newsapp.retrofit

import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.network.model.ArticleDto

internal fun ArticleDto.toArticle(): Article {
    return Article(
        title = title,
        url = url,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt.toString()

    )
}

