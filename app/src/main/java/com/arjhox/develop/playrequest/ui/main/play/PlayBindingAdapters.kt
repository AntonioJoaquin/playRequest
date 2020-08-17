package com.arjhox.develop.playrequest.ui.main.play

import android.webkit.WebView
import androidx.databinding.BindingAdapter

@BindingAdapter("data")
fun WebView.setData(data: String?) {
    data?.let {
        if (it.isNotEmpty()) {
            loadData(data, "application/json", "UTF-8")
        }
    }
}