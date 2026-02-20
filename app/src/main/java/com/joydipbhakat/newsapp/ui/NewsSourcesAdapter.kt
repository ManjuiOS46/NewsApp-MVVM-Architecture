package com.joydipbhakat.newsapp.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.joydipbhakat.newsapp.data.models.NewsSources
import com.joydipbhakat.newsapp.databinding.NewssourcesItemBinding

class NewsSourcesAdapter : RecyclerView.Adapter<NewsSourcesAdapter.NewsSourcesViewHolder>() {
    private var arrayList: ArrayList<NewsSources> = ArrayList()

    class NewsSourcesViewHolder(private val binding: NewssourcesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(newsSources: NewsSources) {
            binding.newsSourcesItemButton.text = newsSources.name
            binding.newsSourcesItemButton.setOnClickListener {
                val customTabsIntent = CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .build()
                customTabsIntent.launchUrl(itemView.context, Uri.parse(newsSources.url))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourcesViewHolder {
        val binding =
            NewssourcesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsSourcesViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: NewsSourcesViewHolder, position: Int) {
        holder.onBind(arrayList[position])
    }

    fun addData(data: List<NewsSources>) {
        arrayList.clear()
        arrayList.addAll(data)
        notifyDataSetChanged()
    }
}