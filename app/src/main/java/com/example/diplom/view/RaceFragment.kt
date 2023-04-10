package com.example.diplom.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.diplom.R
import com.example.diplom.RaceViewModel
import com.example.diplom.data.FullRace
import com.example.diplom.databinding.FragmentRaceBinding

class RaceFragment : Fragment() {
    lateinit var viewModel: RaceViewModel
    lateinit var binding: FragmentRaceBinding
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(requireActivity())[RaceViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRaceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currRaceInfo.observe(viewLifecycleOwner){
            if(it != null){
                setupView(it)
            }
        }
    }
    private fun setupView(race: FullRace){
        Log.d("description", race.data.description)
        binding.fullDescriptionTv.text = race.data.description
    }
}