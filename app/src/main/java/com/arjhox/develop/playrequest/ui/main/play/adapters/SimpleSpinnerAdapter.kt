package com.arjhox.develop.playrequest.ui.main.play.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.arjhox.develop.playrequest.databinding.ItemSimpleSpinnerBinding

class SimpleSpinnerAdapter(
    private val items: List<String>
): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewHolder = if (convertView != null) {
            convertView.tag as ViewHolder
        } else {
            ViewHolder.from(parent)
        }
        val binding = viewHolder.bind(getItem(position))
        val view = binding.root

        view.tag = viewHolder

        return view
    }

    override fun getItem(position: Int): String =
        items[position]

    override fun getItemId(position: Int): Long =
        position.toLong()

    override fun getCount(): Int =
        items.size


    class ViewHolder private constructor(
        private val binding: ItemSimpleSpinnerBinding
    ) {

        companion object {
            fun from(parent: ViewGroup?): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent?.context)
                val binding = ItemSimpleSpinnerBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }


        fun bind(item: String): ItemSimpleSpinnerBinding {
            binding.headerType = item

            binding.executePendingBindings()

            return binding
        }

    }

}