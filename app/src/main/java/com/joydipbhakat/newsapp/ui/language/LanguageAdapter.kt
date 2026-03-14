package com.joydipbhakat.newsapp.ui.language

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joydipbhakat.newsapp.databinding.LanguagesItemBinding

class LanguageAdapter : RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    private val arrayList = ArrayList<String>()

    private lateinit var binding: LanguagesItemBinding

    private var onItemClick: ((String,String) -> Unit)? = null

    private val selectedLanguage = LinkedHashSet<String>()


    inner class LanguageViewHolder(private val binding: LanguagesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(items: String) {
            binding.languagesItemButton.text = items
            binding.root.isSelected = selectedLanguage.contains(items)
            if(binding.root.isSelected)
                binding.languagesImageview.visibility = View.VISIBLE
            else
                binding.languagesImageview.visibility = View.GONE
            binding.languagesItemButton.setOnClickListener {
                if (selectedLanguage.contains(items)) {
                    binding.languagesImageview.visibility = View.GONE
                    selectedLanguage.remove(items)
                } else {
                    if (selectedLanguage.size < 2) {
                        binding.languagesImageview.visibility = View.VISIBLE
                        selectedLanguage.add(items)
                    }
                }
                notifyItemChanged(bindingAdapterPosition)
                if (selectedLanguage.size == 2) {
                    val languages = selectedLanguage.toList()
                    onItemClick?.invoke(languages[0], languages[1])
                }
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


    fun setItemClickListener(listener: (String,String) -> Unit) {
        onItemClick = listener
    }
}
