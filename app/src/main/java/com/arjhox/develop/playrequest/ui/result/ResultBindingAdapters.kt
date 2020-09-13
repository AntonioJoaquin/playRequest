package com.arjhox.develop.playrequest.ui.result

import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.arjhox.develop.playrequest.ui.common.isHtmlResponse
import com.yuyh.jsonviewer.library.JsonRecyclerView

@BindingAdapter("bindJson")
fun JsonRecyclerView.bindJson(response: String?) {
    if (!response.isNullOrBlank() && !isHtmlResponse(response)) {
        bindJson(response)
    }
}


@BindingAdapter("showJsonResponse")
fun HorizontalScrollView.show(response: String?) {
    visibility = if (response.isNullOrBlank() || isHtmlResponse(response)) {
        View.GONE
    } else {
        View.VISIBLE
    }
}


@BindingAdapter("setResponse")
fun TextView.set(response: String?) {
    if (!response.isNullOrBlank() && isHtmlResponse(response)) {
        text = response
    }
}

@BindingAdapter("showResponse")
fun TextView.show(response: String?) {
    visibility = if (response.isNullOrBlank() || !isHtmlResponse(response)) {
        View.GONE
    } else {
        View.VISIBLE
    }
}