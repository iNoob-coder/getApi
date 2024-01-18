package com.example.assment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assment.databinding.ItemLayoutBinding
import com.example.assment.model.PostData

class Adapter(private val list: List<PostData.PostDataItem>, private val listener: ItemClicked): RecyclerView.Adapter<Adapter.ViewHolder>() {

    interface ItemClicked {
        fun onItemClicked(id: Int)
    }

    class ViewHolder(val binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]

        holder.binding.title.text = "${data.id} ${data.title}"
        holder.binding.body.text = data.body

        holder.itemView.setOnClickListener {
            listener.onItemClicked(data.id)
        }

    }
}