package com.example.diplom

import androidx.recyclerview.widget.DiffUtil
import com.example.diplom.data.Race

class RaceDiffCallback: DiffUtil.ItemCallback<Race>() {
    override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
        return oldItem == newItem
    }

}