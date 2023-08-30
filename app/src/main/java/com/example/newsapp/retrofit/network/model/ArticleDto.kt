package com.example.newsapp.retrofit.network.model

import com.example.newsapp.utils.DateSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@ExperimentalSerializationApi
@Serializable
data class ArticleDto(
    @SerialName("source") val source: SourceDto? = null,
    @SerialName("title") val title: String = "",
    @SerialName("url") val url: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("author") val author: String? = null,
    @SerialName("urlToImage") val urlToImage: String? = null,
    @Serializable(with = DateSerializer::class)
    @SerialName("publishedAt") val publishedAt: Date,
    @SerialName("content") val content: String? = null,
)