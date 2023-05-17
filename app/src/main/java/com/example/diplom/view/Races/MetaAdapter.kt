package com.example.diplom.view.Races

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R

class MetaAdapter : ListAdapter<Int, MetaAdapter.MetaViewHolder>(MetaDiffUtilCallBack()) {

    class MetaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val number: TextView = view.findViewById(R.id.meta_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaViewHolder {
        return MetaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.meta_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MetaViewHolder, position: Int) {
        holder.number.text = getItem(position).toString()
    }
}

class MetaDiffUtilCallBack : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
        return oldItem == newItem
    }

}