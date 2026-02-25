package com.joydipbhakat.newsapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joydipbhakat.newsapp.data.models.Countries
import com.joydipbhakat.newsapp.databinding.CountriesItemBinding

class CountriesAdapter : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    private val arrayList = ArrayList<Countries>(emptyList())

    private lateinit var binding: CountriesItemBinding

    private var onItemClick: ((Countries) -> Unit)? = null


    inner class CountriesViewHolder(private val binding: CountriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(items: Countries) {
            binding.countriesItemButton.text = items.name
            binding.countriesItemButton.setOnClickListener {
                onItemClick?.invoke(items)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        binding = CountriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountriesViewHolder(binding)
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.onBind(arrayList[position])
    }

    fun addData(countries: List<Countries>) {
        arrayList.clear()
        arrayList.addAll(countries)
        notifyDataSetChanged()
    }


    fun setItemClickListener(listener: (Countries) -> Unit) {
        onItemClick = listener
    }
}