package com.example.diplom.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.R
import com.example.diplom.RaceListAdapter
import com.example.diplom.RaceViewModel

class RacesListFragment : Fragment() {
    private lateinit var viewModel: RaceViewModel
    private lateinit var raceAdapter: RaceListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RaceViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_races_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        viewModel.raceList.observe(viewLifecycleOwner) {
            raceAdapter.submitList(it.data)
        }
        viewModel.getRaces("2022-06-15", "2022-12-15")
    }

    private fun setupAdapter() {
        raceAdapter = RaceListAdapter(requireContext())
        val recycler: RecyclerView = requireActivity().findViewById(R.id.races_rv)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = raceAdapter
        raceAdapter.onRaceClickListener = {
            viewModel.getRaceInfo(it.uid)
            parentFragmentManager.beginTransaction().add(R.id.container, RaceFragment())
                .addToBackStack(null)
                .commit()
        }
    }

}