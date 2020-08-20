package com.arjhox.develop.playrequest.ui.main.play.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arjhox.develop.playrequest.databinding.ItemHeaderBinding
import com.arjhox.develop.playrequest.ui.common.Header

class HeaderAdapter: ListAdapter<Header, HeaderAdapter.ViewHolder>(HeaderCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ViewHolder private constructor(
        private val binding: ItemHeaderBinding
    ): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeaderBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }


        fun bind(item: Header) {
            binding.header = item
            binding.executePendingBindings()
        }

    }

}


class HeaderCallback: DiffUtil.ItemCallback<Header>() {

    override fun areItemsTheSame(oldItem: Header, newItem: Header): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Header, newItem: Header): Boolean =
        oldItem == newItem

}