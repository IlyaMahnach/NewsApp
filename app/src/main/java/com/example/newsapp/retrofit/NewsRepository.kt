package com.example.newsapp.retrofit

import androidx.paging.*
import androidx.room.withTransaction
import com.example.newsapp.Constants.Companion.DEFAULT_PREFETCH
import com.example.newsapp.Constants.Companion.INITIAL_LOAD_SIZE
import com.example.newsapp.paging.RemoteMediator
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.network.EverythingNewsPagingSource
import com.example.newsapp.retrofit.network.NewsService
import com.example.newsapp.retrofit.network.NewsService.Companion.DEFAULT_PAGE_SIZE
import com.example.newsapp.storage.AppDatabase
import com.example.newsapp.utils.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val everythingNewsPagingSourceFactory: EverythingNewsPagingSource.Factory,
    private val newsService: NewsService,
    private val appDatabase: AppDatabase

) {

    fun queryAll(): PagingSource<Int, Article> {
        return everythingNewsPagingSourceFactory.create(QUERY)
    }

    private companion object {
        private const val QUERY = "ios"
    }

    @ExperimentalPagingApi
    fun getNewsFromMediator(): Flow<PagingData<Article>> {
        val pagingSourceFactory = { appDatabase.newsDao().pagingSource() }
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                maxSize = DEFAULT_PREFETCH * 2 + DEFAULT_PAGE_SIZE,
                prefetchDistance = DEFAULT_PREFETCH,
                enablePlaceholders = true
            ),
            remoteMediator = RemoteMediator(appDatabase, newsService),
            pagingSourceFactory = pagingSourceFactory
        ).flow
            .map { pagingData ->
                pagingData.map { it.toUiModel() }
            }
    }

    suspend fun databaseIsEmpty(): Boolean =
        appDatabase.withTransaction { appDatabase.newsDao().getNewsList().isEmpty() }
}