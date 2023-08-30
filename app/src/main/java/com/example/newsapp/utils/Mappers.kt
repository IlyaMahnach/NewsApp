package com.example.newsapp.utils

import android.text.format.DateUtils
import com.example.newsapp.Constants.Companion.DEFAULT_DESC_SIZE
import com.example.newsapp.Constants.Companion.DEFAULT_TITLE_SIZE
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.network.model.ArticleDto
import com.example.newsapp.storage.news.NewsEntity
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
internal fun ArticleDto.toEntity(): NewsEntity {
    return NewsEntity(
        title = if (title.length > DEFAULT_TITLE_SIZE) {
            title.substring(0, DEFAULT_TITLE_SIZE).plus("...")
        } else {
            title
        },
        url = url,
        description = if (description?.length!! > DEFAULT_DESC_SIZE) {
            description.substring(0, DEFAULT_DESC_SIZE).plus("...")
        } else {
            description
        },
        urlToImage = urlToImage,
        publishedAt = DateUtils.getRelativeTimeSpanString(publishedAt.time) as String?
    )
}

internal fun NewsEntity.toUiModel(): Article {
    return Article(
        title = title,
        url = url,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt

    )
}