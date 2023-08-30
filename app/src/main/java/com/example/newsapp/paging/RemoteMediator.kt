package com.example.newsapp.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.newsapp.Constants.Companion.INITIAL_PAGE_NUMBER
import com.example.newsapp.Constants.Companion.LAST_PAGE
import com.example.newsapp.retrofit.network.NewsService
import com.example.newsapp.storage.AppDatabase
import com.example.newsapp.storage.news.NewsEntity
import com.example.newsapp.storage.remotekey.RemoteKeysEntity
import com.example.newsapp.utils.toEntity
import kotlinx.serialization.ExperimentalSerializationApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalPagingApi
class RemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val newsService: NewsService
) : RemoteMediator<Int, NewsEntity>() {

    private val newsDao = database.newsDao()
    private val remoteKeyDao = database.remoteKeysDao()

    @ExperimentalSerializationApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewsEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val refreshKey = getClosestRemoteKeys(state)
                    refreshKey?.nextKey?.minus(1) ?: INITIAL_PAGE_NUMBER
                }
                LoadType.APPEND -> {
                    val remoteKeys = getLastRemoteKey(state)
                    remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            val isLastPage = page == LAST_PAGE
            val response = newsService.everything(page = page, pageSize = state.config.maxSize)
            val listOfArticles = response.body()!!.articles.map { it.toEntity() }
            if (response.isSuccessful) {
                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        newsDao.clearAll()
                        remoteKeyDao.clearAll()
                    }

                    val nextKey = if (isLastPage) null else page + 1
                    val keys = listOfArticles.map {
                        RemoteKeysEntity(id = it.title, nextKey = nextKey)
                    }
                    remoteKeyDao.insertAll(keys)
                    newsDao.insertAll(listOfArticles)
                }

            } else throw HttpException(response)
            return MediatorResult.Success(endOfPaginationReached = isLastPage)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getClosestRemoteKeys(state: PagingState<Int, NewsEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let { entity ->
                remoteKeyDao.getRemoteKeys(entity.title)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, NewsEntity>): RemoteKeysEntity? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { entity -> remoteKeyDao.getRemoteKeys(entity.title) }
    }
}