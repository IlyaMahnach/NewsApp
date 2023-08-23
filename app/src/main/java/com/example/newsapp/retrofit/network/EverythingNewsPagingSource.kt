package com.example.newsapp.retrofit.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.toArticle
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException


class EverythingNewsPagingSource @AssistedInject constructor(
    private val newsService: NewsService,
    @Assisted("query") private val query: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val pageSize: Int = params.loadSize.coerceAtMost((NewsService.MAX_PAGE_SIZE))

        val response = newsService.everything(query, page, pageSize)
        if (response.isSuccessful) {
            val articles = checkNotNull(response.body()).articles.map { it.toArticle() }
            val nextKey = if (articles.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            return LoadResult.Page(articles, prevKey, nextKey)
        } else {
            return LoadResult.Error(HttpException(response))
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("query") query: String): EverythingNewsPagingSource
    }


}

