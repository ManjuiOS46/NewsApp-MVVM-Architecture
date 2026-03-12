package com.joydipbhakat.newsapp

import android.view.View
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    protected fun showLoading(
        progressBar: View,
        errorLayout: View
    ) {
        progressBar.visibility = View.VISIBLE
        errorLayout.visibility = View.GONE
    }

    protected fun showError(
        progressBar: View,
        errorLayout: View
    ) {
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.VISIBLE
    }

    protected fun showSuccess(
        progressBar: View,
        errorLayout: View
    ) {
        progressBar.visibility = View.GONE
        errorLayout.visibility = View.GONE
    }


}