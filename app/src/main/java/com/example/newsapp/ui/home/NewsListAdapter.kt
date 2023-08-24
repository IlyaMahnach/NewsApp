package com.example.newsapp.ui.home

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemNewsBinding
import com.example.newsapp.retrofit.model.Article


class NewsListAdapter(private val context: Context) :
    PagingDataAdapter<Article, NewsListAdapter.HomeNewsViewHolder>(ArticleDiffItemCallback) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewsViewHolder {
        return HomeNewsViewHolder(layoutInflater.inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: HomeNewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class HomeNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewBinding by viewBinding(ItemNewsBinding::bind)

        fun bind(article: Article?) {

            viewBinding.clickAt.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(
                    context, Uri.parse(article?.url)
                )

            }



            with(viewBinding) {
                image.load(article?.urlToImage) {
                    placeholder(ColorDrawable(Color.TRANSPARENT))
                }
                title.text = article?.title
                publishedAt.text = article?.publishedAt
                description.text = article?.description
            }
        }
    }
}


private object ArticleDiffItemCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title && oldItem.url == newItem.url && oldItem.publishedAt == newItem.publishedAt && oldItem.description == newItem.description

    }
}



