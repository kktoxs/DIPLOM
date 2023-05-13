package com.example.diplom.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplom.R
import com.example.diplom.RaceListAdapter
import com.example.diplom.RaceViewModel
import com.example.diplom.databinding.FragmentRaceListBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar

class RaceListFragment : Fragment() {
    private lateinit var viewModel: RaceViewModel
    private lateinit var binding: FragmentRaceListBinding
    private lateinit var raceAdapter: RaceListAdapter
    private var galleryIsEmpty: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRaceListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[RaceViewModel::class.java]
        viewModel.raceList.observe(viewLifecycleOwner) {
            raceAdapter.submitList(it.data)
            binding.racesNotFound.isVisible = it.data.isEmpty()
        }
        viewModel.currentDates.observe(viewLifecycleOwner) {
            viewModel.getRaces(it.first, it.second)
            binding.fromTv.text = it.first
            binding.toTv.text = it.second
        }

        viewModel.galleryIsNull.observe(viewLifecycleOwner) {
            galleryIsEmpty = it
            Log.d("isEmpty", galleryIsEmpty.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        binding.toolbar.setOnClickListener {
            val builder = MaterialDatePicker.Builder.dateRangePicker()
            val now = Calendar.getInstance()
            builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
            val picker = builder.build()
            picker.show(activity?.supportFragmentManager!!, picker.toString())
            picker.addOnPositiveButtonClickListener {
                viewModel.selectDates(getDate(it.first), getDate(it.second))
            }
        }
    }

    private fun getDate(mills: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = mills
        return formatter.format(calendar.time)
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
        raceAdapter.onGalleryButtonClickListener = {
            viewModel.getPreviews(it.uid, 0)
            val bundle = Bundle()
            bundle.putString("uid", it.uid)
            findNavController().navigate(R.id.action_races_to_galleryFragment, bundle)
        }
    }
}