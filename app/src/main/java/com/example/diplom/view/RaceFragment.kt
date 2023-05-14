package com.example.diplom.view

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.bumptech.glide.Glide
import com.example.diplom.RaceViewModel
import com.example.diplom.data.FullRace
import com.example.diplom.databinding.FragmentRaceBinding
import com.example.diplom.databinding.FragmentRaceNewBinding

class RaceFragment : Fragment() {
    lateinit var viewModel: RaceViewModel
    lateinit var binding: FragmentRaceNewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[RaceViewModel::class.java]
        binding = FragmentRaceNewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currRaceInfo.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.progressBar.visibility = View.GONE
                setupView(it)
                Log.d("fullRace", it.toString())
            }
        }
    }

    private fun setupView(race: FullRace) {
        binding.nameTv.text = race.data.name
        //binding.cityTv.text = race.data.city
        //binding.competitorsTv.text = race.data.competitorsCount.toString()
        binding.descriptionTv.text = race.data.description

        Glide.with(requireContext())
            .load("https://fget.marshalone.ru/files/race/uid/" + race.data.logo)
            .into(binding.logoIv)

        Glide.with(requireContext())
            .load("https://fget.marshalone.ru/files/race/uid/" + race.data.titlePicture)
            .into(binding.titleIv)

        setupAdapter(
            race.data.city,
            race.data.date,
            race.data.sportType,
            race.data.competitorsCount
        )
    }

    private fun setupAdapter(place: String, date: String, raceType: String, competitors: Int) {
        val carouselAdapter = CarouselAdapter(place, date, raceType, competitors)
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((15 * Resources.getSystem().displayMetrics.density).toInt()))
        binding.infoVp.apply {
            clipChildren = false
            // Show the viewpager in full width without clipping the padding
            clipToPadding = false
            // Render the left and right items
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
            adapter = carouselAdapter
            setPageTransformer(compositePageTransformer)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //viewModel.closeRace()
    }
}