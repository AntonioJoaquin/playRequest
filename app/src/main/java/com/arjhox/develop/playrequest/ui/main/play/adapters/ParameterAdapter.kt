package com.arjhox.develop.playrequest.ui.main.play.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arjhox.develop.playrequest.R
import com.arjhox.develop.playrequest.databinding.ItemParameterBinding
import com.arjhox.develop.playrequest.ui.common.Parameter
import com.arjhox.develop.playrequest.ui.common.bindingInflate
import com.arjhox.develop.playrequest.ui.main.play.parameter.ParameterListener

class ParameterAdapter(
    private val parameterListener: ParameterListener
): RecyclerView.Adapter<ParameterAdapter.ViewHolder>() {

    var items = listOf<Parameter>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position, parameterListener)
    }


    class ViewHolder private constructor(
        private val binding: ItemParameterBinding
    ): RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = parent.bindingInflate<ItemParameterBinding>(R.layout.item_parameter)

                return ViewHolder(binding)
            }
        }


        fun bind(item: Parameter, position: Int, parameterListener: ParameterListener) {
            binding.parameter = item
            binding.position = position
            binding.parameterListener = parameterListener

            binding.executePendingBindings()
        }

    }

}