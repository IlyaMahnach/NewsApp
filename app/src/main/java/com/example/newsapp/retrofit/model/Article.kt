package com.example.newsapp.retrofit.model

data class Article(
    val title: String,
    val url: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
)