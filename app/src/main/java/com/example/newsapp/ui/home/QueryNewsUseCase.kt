package com.example.newsapp.ui.home

import androidx.paging.PagingSource
import com.example.newsapp.retrofit.NewsRepository
import com.example.newsapp.retrofit.model.Article
import javax.inject.Inject

class QueryNewsUseCase @Inject constructor(private val repository: NewsRepository) {

    operator fun invoke(query: String): PagingSource<Int, Article> {
        return repository.queryAll()
    }
}