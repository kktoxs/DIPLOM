package com.example.diplom.view.Races

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.R
import com.example.diplom.data.Images


class GalleryAdapter(private val context: Context, private val callback: (Int) -> Unit) :
    ListAdapter<String, GalleryAdapter.GalleryViewHolder>(GalleryDiffUtilCallback()) {
    var onPhotoClickListener: ((String) -> Unit)? = null

    class GalleryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.preview_image)
        //val num: TextView = view.findViewById(R.id.pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_item, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val previewURL = getItem(position)

        //Log.d("url", previewURL)
        Glide.with(context)
            .load("https://photo.marshalone.ru/api/photo/file/get?type=resized&UUID=$previewURL")
            .into(holder.image)

        holder.image.setOnClickListener {
            onPhotoClickListener?.invoke(previewURL)
        }
        //holder.num.text = position.toString()

        if (itemCount - position == 1) {
            callback(itemCount)
            //Log.d("item count", itemCount.toString())
            //Log.d("position", position.toString())
        }
    }

    fun updateList(newData: List<String>) {
        if (newData.isNotEmpty()) {
            val newList = currentList + newData
            submitList(newList)
            Log.d("updateList", newList.size.toString())
        } else {
            Log.d("updateList", "empty")
        }
    }
}

class GalleryDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem.length == newItem.length
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }


}