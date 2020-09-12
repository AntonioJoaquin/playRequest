package com.arjhox.develop.playrequest.ui.main.play.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.ItemHeaderBinding
import com.arjhox.develop.playrequest.ui.common.extensions.bindingInflate
import com.arjhox.develop.playrequest.ui.common.models.Header
import com.arjhox.develop.playrequest.ui.main.play.header.HeaderListener

class HeaderAdapter(
    private val headerListener: HeaderListener
): RecyclerView.Adapter<HeaderAdapter.ViewHolder>() {

    var items = listOf<Header>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position, headerListener)
    }

    override fun getItemCount(): Int =
        items.size


    class ViewHolder private constructor(
        private val binding: ItemHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = parent.bindingInflate<ItemHeaderBinding>(R.layout.item_header)

                return ViewHolder(binding)
            }
        }


        fun bind(item: Header, position: Int, headerListener: HeaderListener) {
            binding.header = item
            binding.position = position
            binding.headerListener = headerListener

            binding.executePendingBindings()
        }

    }

}