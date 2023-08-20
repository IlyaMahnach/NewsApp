package com.example.newsapp.retrofit

import androidx.paging.PagingSource
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.network.EverythingNewsPagingSource
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val everythingNewsPagingSourceFactory: EverythingNewsPagingSource.Factory
) {

    fun queryAll(): PagingSource<Int, Article> {
        return everythingNewsPagingSourceFactory.create(QUERY)
    }
    private companion object{
        private const val QUERY = "ios"
    }
}