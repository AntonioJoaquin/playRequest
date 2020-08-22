package com.arjhox.develop.playrequest.ui.main.play

import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doOnTextChanged
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import com.arjhox.develop.domain.common.LoadingState
import com.arjhox.develop.playrequest.ui.common.Header
import com.arjhox.develop.playrequest.ui.main.play.adapters.HeaderAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

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

@BindingAdapter("show")
fun RecyclerView.show(items: List<*>?) {
    visibility = if (items.isNullOrEmpty()) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("bindAdapter")
fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>) {
    this.run {
        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}

@BindingAdapter("items")
fun RecyclerView.items(data: List<Header>?) {
    (adapter as? HeaderAdapter)?.let {
        it.items = data ?: emptyList()
    }
}

@BindingAdapter("itemSelected")
fun AppCompatSpinner.itemSelected(result: ObservableField<String>) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("showInputError")
fun TextInputLayout.showInputError(value: String?) {
    error = if (value==null) {
        "Header value is necessary"
    } else {
        null
    }
}

@BindingAdapter("enteredText")
fun TextInputEditText.enteredText(result: ObservableField<String>) {
    doOnTextChanged { text, _, _, _ ->
        result.set(text.toString())
    }
}