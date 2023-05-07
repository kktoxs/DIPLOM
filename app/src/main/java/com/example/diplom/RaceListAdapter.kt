package com.example.diplom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.data.Race
import com.google.android.material.button.MaterialButton

class RaceListAdapter(
    private val context: Context,
    //private val checkIfGalleryIsEmpty: (String) -> Boolean
) :
    ListAdapter<Race, RaceListAdapter.RaceViewHolder>(RaceDiffCallback()) {

    var onRaceClickListener: ((Race) -> Unit)? = null
    var onGalleryButtonClickListener: ((Race) -> Unit)? = null

    class RaceViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title_tv)
        val date: TextView = view.findViewById(R.id.date_tv)
        val raceType: TextView = view.findViewById(R.id.race_type_tv)
        val racersCount: TextView = view.findViewById(R.id.racers_count_tv)
        val place: TextView = view.findViewById(R.id.place_tv)
        val image: ImageView = view.findViewById(R.id.race_iv)
        val galleryButton: MaterialButton = view.findViewById(R.id.gallery_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.new_race_item,
            parent,
            false
        )
        return RaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) {
        val race = getItem(position)
        holder.title.text = race.name
        holder.raceType.text = race.sportType
        holder.place.text = race.city
        holder.racersCount.text = race.competitorsCount.toString()
        holder.date.text = race.date

        //holder.galleryButton.isVisible = checkIfGalleryIsEmpty(race.uid)

        Glide.with(context)
            .load("https://fget.marshalone.ru/files/race/uid/" + race.titlePicture)
            .into(holder.image)

        holder.view.setOnClickListener {
            onRaceClickListener?.invoke(race)
        }

        holder.galleryButton.setOnClickListener {
            onGalleryButtonClickListener?.invoke(race)
        }

    }
}

class RaceDiffCallback : DiffUtil.ItemCallback<Race>() {
    override fun areItemsTheSame(oldItem: Race, newItem: Race): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: Race, newItem: Race): Boolean {
        return oldItem == newItem
    }

}