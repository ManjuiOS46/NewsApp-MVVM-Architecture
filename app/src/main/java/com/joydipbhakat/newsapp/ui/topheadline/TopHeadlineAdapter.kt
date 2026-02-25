package com.joydipbhakat.newsapp.ui.topheadline

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joydipbhakat.newsapp.data.models.Articles
import com.joydipbhakat.newsapp.databinding.TopheadlineItemBinding
import javax.inject.Inject

class TopHeadlineAdapter @Inject constructor():
    RecyclerView.Adapter<TopHeadlineAdapter.TopHeadlineViewHolder>() {
    private val arrayList = ArrayList<Articles>()

    class TopHeadlineViewHolder(private val binding: TopheadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(articles: Articles) {
            binding.textViewTitle.text = articles.title
            binding.textViewDescription.text = articles.description
            binding.textViewSource.text = articles.source?.name
            Glide.with(binding.imageViewBanner.context).load(articles.urlToImage)
                .into(binding.imageViewBanner)
            itemView.setOnClickListener {
                val customTabsIntent = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()

                customTabsIntent.launchUrl(
                    itemView.context,
                    Uri.parse(articles.url)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlineViewHolder {
        val binding = TopheadlineItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TopHeadlineViewHolder(binding)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: TopHeadlineViewHolder, position: Int) {
        holder.onBind(arrayList[position])
    }


    fun addData(list: List<Articles>) {
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }
}