package com.joydipbhakat.newsapp.ui

import UIState
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.joydipbhakat.newsapp.NewsApplication
import com.joydipbhakat.newsapp.databinding.ActivityNewssourcesBinding
import com.joydipbhakat.newsapp.di.ActivityContext
import com.joydipbhakat.newsapp.di.ActivityScope
import com.joydipbhakat.newsapp.di.component.DaggerNewsSourcesComponent
import com.joydipbhakat.newsapp.di.component.NewsSourcesComponent
import com.joydipbhakat.newsapp.di.module.NewsSourcesActivityModule
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsSourcesActivity : AppCompatActivity() {
    @ActivityScope
    private lateinit var newsSourcesComponent: NewsSourcesComponent

    @Inject
    @ActivityContext
    lateinit var context: Context

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var newsSourcesAdapter: NewsSourcesAdapter

    private lateinit var newsSourcesViewModel: NewsSourcesViewModel

    private lateinit var binding: ActivityNewssourcesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        binding = ActivityNewssourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        newsSourcesViewModel =
            ViewModelProvider(this, viewModelFactory)[NewsSourcesViewModel::class.java]
        setUpUI()
        setUpObserver()
    }

    private fun setUpUI() {
        binding.newsSourcesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.newsSourcesRecyclerView.adapter = newsSourcesAdapter
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsSourcesViewModel.uiState.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding.newsSourcesProgressBar.visibility = View.GONE
                            newsSourcesAdapter.addData(it.data)
                        }
                        is UIState.Error -> {
                            binding.newsSourcesProgressBar.visibility = View.GONE
                            Toast.makeText(this@NewsSourcesActivity, it.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        UIState.Loading -> {
                            binding.newsSourcesProgressBar.visibility = View.VISIBLE
                        }
                        else -> {

                        }
                    }
                }
            }
        }

    }

    private fun injectDependencies() {
        newsSourcesComponent = DaggerNewsSourcesComponent.builder().applicationComponent(
            (application as NewsApplication).applicationComponent
        ).newsSourcesActivityModule(NewsSourcesActivityModule(this)).build()

        newsSourcesComponent.inject(this)
    }
}