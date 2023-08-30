package com.example.newsapp.di

import android.app.Application
import com.example.newsapp.storage.AppDatabase
import com.example.newsapp.storage.news.NewsDao
import com.example.newsapp.storage.remotekey.RemoteKeysDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideNewsDao(database: AppDatabase): NewsDao {
        return database.newsDao()
    }

    @Singleton
    @Provides
    fun provideKeysDao(database: AppDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }
}