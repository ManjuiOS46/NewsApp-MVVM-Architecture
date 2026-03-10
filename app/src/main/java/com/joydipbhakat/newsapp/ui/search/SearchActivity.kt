package com.joydipbhakat.newsapp.ui.search

import UIState
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.databinding.ActivitySearchBinding
import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.component.DaggerSearchComponent
import com.joydipbhakat.newsapp.di.component.SearchComponent
import com.joydipbhakat.newsapp.di.module.SearchActivityModule
import com.joydipbhakat.newsapp.ui.ViewModelFactory
import com.joydipbhakat.newsapp.ui.topheadline.TopHeadlineAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {

    @ActivityScope
    lateinit var searchComponent: SearchComponent

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var adapter: TopHeadlineAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var searchViewModel: SearchViewModel

    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchViewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        binding.searchViewEditText.addTextChangedListener {
            if (it?.isNotEmpty() == true) {
                searchViewModel.newsSearch(it.toString())
            }
        }
        setUpUi()
        setUpObserver()
    }

    private fun setUpUi() {
        binding.apply {
            searchRecyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
            searchRecyclerView.adapter = adapter
        }
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                searchViewModel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.searchProgressBar.visibility = View.GONE
                            it.data.collect{it1->
                                adapter.addData(it1)
                            }
                        }
                        is UIState.Error -> {
                            binding.searchProgressBar.visibility = View.GONE
                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        is UIState.Loading -> {
                            binding.searchProgressBar.visibility = View.VISIBLE
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun injectDependencies() {
        searchComponent = DaggerSearchComponent.builder()
            .applicationComponent((application as NewsApplication).applicationComponent)
            .searchActivityModule(SearchActivityModule(this)).build()

        searchComponent.inject(this)
    }
}