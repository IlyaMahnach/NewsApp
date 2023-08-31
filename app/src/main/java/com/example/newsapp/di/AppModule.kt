package com.example.newsapp.di

import android.app.Application
import coil.ImageLoader
import com.example.newsapp.BuildConfig
import com.example.newsapp.Dispatchers
import com.example.newsapp.retrofit.network.AuthInterceptor
import com.example.newsapp.retrofit.network.NewsService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideNewsService(): NewsService {
        val httpClient =
            OkHttpClient.Builder().addInterceptor(AuthInterceptor(BuildConfig.NEWS_API_KEY)).build()

        val retrofit = Retrofit.Builder().baseUrl("https://newsapi.org").client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

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