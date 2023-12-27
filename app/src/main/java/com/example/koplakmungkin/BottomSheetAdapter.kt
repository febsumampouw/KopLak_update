package com.example.koplakmungkin

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.koplakmungkin.databinding.ListItemBsBinding

class BottomSheetAdapter(private val onItemClick: (String) -> Unit) :
    ListAdapter<String, BottomSheetAdapter.ViewHolder>(DiffCallback()) {

    private var originalList: List<String> = emptyList()
    private var cityList: List<String> = emptyList()

    init {
        originalList = currentList.toList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ListItemBsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.itemTv.text = item
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    fun setCityList(list: List<String>) {
        cityList = list
        originalList = list.toList()
        submitList(list)
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalList.toList()
        } else {
            originalList.filter { item ->
                item.contains(query, ignoreCase = true)
            }
        }

        Log.d("FilteredList", filteredList.toString())

        submitList(filteredList)
    }

    private class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
