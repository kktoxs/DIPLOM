package com.example.diplom.view.Races

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R

class CarouselAdapter(place: String, date: String, raceType: String, competitors: Int) :
    RecyclerView.Adapter<CarouselAdapter.InfoViewHolder>() {

    data class RaceInfo(
        val image: Int,
        val title: String,
        val info: String = "Неизвестно"
    )

    private val info: List<RaceInfo> = listOf(
        RaceInfo(R.drawable.ic_baseline_place_24, "Местоположение", place),
        RaceInfo(R.drawable.ic_baseline_date_range_24, "Дата проведения", date),
        RaceInfo(R.drawable.ic_baseline_directions_bike_24, "Тип гонки", raceType),
        RaceInfo(R.drawable.ic_baseline_groups_24, "Кол-во участников", competitors.toString()),
    )

    class InfoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.info_image)
        val title: TextView = view.findViewById(R.id.title)
        val info: TextView = view.findViewById(R.id.info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.info_carousel_item, parent, false)
        return InfoViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return info.size
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        val infoCard = info[position]
        holder.image.setImageResource(infoCard.image)
        holder.title.text = infoCard.title
        holder.info.text = infoCard.info
    }
}