package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.map
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.appComponent
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.utils.toUiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


class NewsListActivity : AppCompatActivity(R.layout.activity_home) {
    @Inject
    lateinit var newsListViewModel: NewsListViewModel

    private val viewBinding by viewBinding(ActivityHomeBinding::bind)
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsListAdapter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        with(viewBinding) {
            news.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsLoaderStateAdapter(),
                footer = NewsLoaderStateAdapter()
            )
            refreshLayout.setOnRefreshListener {
                adapter.retry()
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.news.collectLatest { newsEntityPagingData ->
                    adapter.submitData(newsEntityPagingData.map { it.toUiModel() })
                }

            }
        }


    }

}
