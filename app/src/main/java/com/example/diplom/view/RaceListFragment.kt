package com.example.diplom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplom.R
import com.example.diplom.RaceListAdapter
import com.example.diplom.RaceViewModel
import com.example.diplom.databinding.FragmentRaceListBinding

class RaceListFragment : Fragment() {
    private lateinit var viewModel: RaceViewModel
    private lateinit var binding: FragmentRaceListBinding
    private lateinit var raceAdapter: RaceListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRaceListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[RaceViewModel::class.java]
        viewModel.raceList.observe(viewLifecycleOwner) {
            raceAdapter.submitList(it.data)
        }
        viewModel.getRaces("2022-06-15", "2022-12-15")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {
        raceAdapter = RaceListAdapter(requireContext())
        binding.racesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = raceAdapter
        }
            raceAdapter.onRaceClickListener = {
            viewModel.getRaceInfo(it.uid)
            findNavController().navigate(R.id.action_races_to_raceFragment)
        }

    }
}