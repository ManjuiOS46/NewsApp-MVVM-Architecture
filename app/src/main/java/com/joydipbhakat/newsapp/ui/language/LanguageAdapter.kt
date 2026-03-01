package com.joydipbhakat.newsapp.ui.language

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joydipbhakat.newsapp.databinding.LanguagesItemBinding

class LanguageAdapter : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    private val arrayList = ArrayList<String>()

    private lateinit var binding: LanguagesItemBinding

    private var onItemClick: ((String) -> Unit)? = null


    inner class LanguageViewHolder(private val binding: LanguagesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(items: String) {
            binding.languagesItemButton.text = items
            binding.languagesItemButton.setOnClickListener {
                onItemClick?.invoke(items)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        binding = LanguagesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LanguageViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.onBind(arrayList[position])
    }

    fun addData(language: List<String>) {
        arrayList.clear()
        arrayList.addAll(language)
        notifyDataSetChanged()
    }


    fun setItemClickListener(listener: (String) -> Unit) {
        onItemClick = listener
    }
}
