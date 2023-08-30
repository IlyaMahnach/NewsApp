package com.example.newsapp.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapp.retrofit.NewsRepository
import com.example.newsapp.retrofit.model.Article
import kotlinx.coroutines.flow.Flow
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

@InjectViewState
class NewsListPresenter @Inject constructor(private val repository: NewsRepository) :
    MvpPresenter<NewsListView>() {

    @ExperimentalPagingApi
    val pagingNews: Flow<PagingData<Article>> =
        repository.getNewsFromMediator().cachedIn(presenterScope)

    @ExperimentalPagingApi
    suspend fun setUpAdapter(context: Context) {
        viewState.initAdapter(connected = checkNetworkConnection(context), isEmptyDb())
    }

    private suspend fun isEmptyDb(): Boolean = repository.databaseIsEmpty()

    private fun checkNetworkConnection(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}