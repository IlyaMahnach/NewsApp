package com.example.newsapp.retrofit.network

import com.example.newsapp.BuildConfig
import com.example.newsapp.retrofit.network.model.ArticlesResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface NewsService {
    @GET("/v2/everything")
    suspend fun getNews(
        @Query("q") query: String = "ios",
        @Query("page") page: Int,
        @Query("from") from: String = "2020-04-00",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Response<ArticlesResponseDto>

    companion object {
        const val DEFAULT_PAGE_SIZE = 5
        const val MAX_PAGE_SIZE = 20
    }
}

