package com.arjhox.develop.playrequest.ui.main.play

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.arjhox.develop.domain.common.LoadingState
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("show")
fun TextView.show(loadingState: LoadingState?) {
    visibility = when(loadingState) {
        LoadingState.LOADED -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("show")
fun FloatingActionButton.show(requestPath: String?) {
    if (requestPath.isNullOrEmpty()) {
        hide()
    } else {
        show()
    }
}