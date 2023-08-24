package com.example.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.network.EverythingNewsPagingSource
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider


class NewsListViewModel @Inject constructor(
    private val pagingSourceFactory: EverythingNewsPagingSource.Factory
) : ViewModel() {
    val news: StateFlow<PagingData<Article>> = Pager(
        PagingConfig(pageSize = 5, prefetchDistance = 3),


        ) {
        pagingSourceFactory.create(QUERY)
    }.flow.cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

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
