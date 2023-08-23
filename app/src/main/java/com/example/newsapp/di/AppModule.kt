package com.example.newsapp.di

import android.app.Application
import coil.ImageLoader
import com.example.newsapp.BuildConfig
import com.example.newsapp.Dispatchers
import com.example.newsapp.retrofit.network.AuthInterceptor
import com.example.newsapp.retrofit.network.NewsService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json(Json.Default) {
            ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideNewsService(json: Json): NewsService {
        val httpClient =
            OkHttpClient.Builder().addInterceptor(AuthInterceptor(BuildConfig.NEWS_API_KEY)).build()

        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org").client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).build()

        return retrofit.create(NewsService::class.java)
    }

    @Singleton
    @Provides
    fun provideDispatchers(): Dispatchers {
        return Dispatchers.Default
    }

    @Singleton
    @Provides
    fun provideImageLoader(application: Application): ImageLoader {
        return ImageLoader(application)
    }
}