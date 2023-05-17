package com.example.diplom.view.Races

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.data.Participant

class ParticipantsAdapter : RecyclerView.Adapter<ParticipantsAdapter.ParticipantsViewHolder>() {
    var onParticipantClickListener: ((Participant) -> Unit)? = null
    private var participantsList = listOf<Participant>()

    @SuppressLint("NotifyDataSetChanged")
    fun setParticipants(newList: List<Participant>) {
        Log.d("list size", newList.size.toString())
        participantsList = newList
        notifyDataSetChanged()
    }

    class ParticipantsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        //val checkbox: CheckBox = view.findViewById(R.id.checkbox)

        val fio: TextView = view.findViewById(R.id.fio)
        val number: TextView = view.findViewById(R.id.number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder {
        return ParticipantsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.participant_item, parent, false)
        )

    }

    override fun getItemCount(): Int =
        participantsList.size


    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int) {
        val participant = participantsList[position]
        holder.fio.text = participant.fio
        holder.number.text = participant.startNumber.toString()
        holder.view.setOnClickListener {
            onParticipantClickListener?.invoke(participant)
        }
        /* holder.checkbox.isChecked = participant.checkedStatus
         holder.checkbox.setOnCheckedChangeListener { compoundButton, b ->
             participant.checkedStatus = true
         }*/
    }
}
