package com.example.newsapp.retrofit.network.model

import androidx.annotation.IntRange
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponseDto(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") @IntRange(from = 1) val totalResults: Int,
    @SerializedName("message") val message: String? = null,
    @SerializedName("articles") val articles: List<ArticleDto>,
)