package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.newsapp.R
import com.example.newsapp.appComponent
import com.example.newsapp.databinding.ActivityHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


class HomeActivity : AppCompatActivity(R.layout.activity_home) {
    @Inject
    lateinit var viewModelProvider: Provider<HomeViewModel.Factory>

    private val viewBinding by viewBinding(ActivityHomeBinding::bind)
    private val homeViewModel: HomeViewModel by viewModels { viewModelProvider.get() }
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        HomeNewsAdapter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        with(viewBinding) {
            news.adapter = adapter.withLoadStateHeaderAndFooter(
                header = NewsLoaderStateAdapter(),
                footer = NewsLoaderStateAdapter()
            )

        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.news.collectLatest(adapter::submitData)

            }
        }

    }

}
