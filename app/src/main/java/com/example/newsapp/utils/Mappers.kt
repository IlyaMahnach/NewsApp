package com.example.newsapp.utils

import com.example.newsapp.Constants
import com.example.newsapp.Constants.Companion.DEFAULT_DESC_SIZE
import com.example.newsapp.Constants.Companion.DEFAULT_TITLE_SIZE
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.network.model.ArticleDto
import com.example.newsapp.storage.news.NewsEntity
import kotlinx.serialization.ExperimentalSerializationApi
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalSerializationApi
internal fun ArticleDto.toEntity(): NewsEntity {
    return NewsEntity(
        title = title,
        url = url!!,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt?.let {
            SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT, Locale.getDefault()).parse(
                it
            )?.toString()
        }
    )
}

fun NewsEntity.toUiModel(): Article {
    return Article(
        title = title,
        url = url,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt

    )
}