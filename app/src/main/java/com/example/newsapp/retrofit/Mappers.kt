package com.example.newsapp.retrofit

import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.model.Source
import com.example.newsapp.retrofit.network.model.ArticleDto
import com.example.newsapp.retrofit.network.model.SourceDto

internal fun ArticleDto.toArticle(): Article {
    return Article(
        source = this.source?.toSource(),
        title = title,
        url = url,
        description = description,
        author = author,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

private fun SourceDto.toSource(): Source {
    return Source(id = id, name = name)
}