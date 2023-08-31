package com.example.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.newsapp.Constants
import com.example.newsapp.retrofit.network.NewsService
import com.example.newsapp.storage.AppDatabase
import com.example.newsapp.storage.news.NewsEntity
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class NewsListViewModel @Inject constructor(
    database: AppDatabase,
    newsService: NewsService
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val news: Flow<PagingData<NewsEntity>> = Pager(
        config = PagingConfig(
            pageSize = NewsService.DEFAULT_PAGE_SIZE,
            initialLoadSize = Constants.INITIAL_LOAD_SIZE,
            maxSize = Constants.DEFAULT_PREFETCH * 2 + NewsService.DEFAULT_PAGE_SIZE,
            prefetchDistance = Constants.DEFAULT_PREFETCH,
            enablePlaceholders = true
        ),
        remoteMediator = com.example.newsapp.paging.RemoteMediator(database, newsService),
        pagingSourceFactory = { database.newsDao().getAllNews() }
    )
        .flow
        .cachedIn(viewModelScope)

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModelProvider: Provider<NewsListViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == NewsListViewModel::class.java)
            return viewModelProvider.get() as T
        }
    }

    private companion object {
        private const val QUERY = "ios"
    }
}
