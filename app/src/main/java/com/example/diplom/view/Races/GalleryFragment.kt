package com.example.diplom.view.Races

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diplom.R
import com.example.diplom.databinding.FragmentGalleryBinding
import java.lang.RuntimeException


class GalleryFragment : Fragment() {
    private lateinit var binding: FragmentGalleryBinding
    private lateinit var viewModel: RaceViewModel
    private lateinit var galleryAdapter: GalleryAdapter
    private lateinit var currRaceUID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currRaceUID =
            arguments?.getString("uid")
                ?: throw RuntimeException("Unknown race $currRaceUID")
        viewModel = ViewModelProvider(requireActivity())[RaceViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        viewModel.currentParticipant.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.currPartTv.text = "Участник " + it
                Log.d("Fragment", "Participant changed $it")
                galleryAdapter.submitList(listOf())
            }
        }
        viewModel.previews.observe(viewLifecycleOwner) {
            if (it != null) {
                galleryAdapter.updateList(it.previewURL)
            }
            binding.noPhotos.isVisible = (it == null) && (galleryAdapter.itemCount == 0)
            // binding.participantsListButton.isVisible = (it != null)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        binding.participantsListButton.setOnClickListener {
            viewModel.clearParticipant()
            findNavController().navigate(R.id.action_galleryFragment_to_participantsListFragment)
        }
        Log.d("Fragment", "onViewCreated " + viewModel.currentParticipant.value.toString())
        viewModel.getAllParticipants(currRaceUID)
    }

    private fun setupAdapter() {
        viewModel.getPreviews(currRaceUID, 0)
        galleryAdapter = GalleryAdapter(requireContext()) {
            viewModel.getPreviews(currRaceUID, it)
        }
        galleryAdapter.onPhotoClickListener = {
            viewModel.openPhotoMeta(it)
            findNavController().navigate(R.id.action_galleryFragment_to_metaFragment)
        }
        //galleryAdapter.submitList(listOf())
        binding.galleryRv.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onStop() {
        super.onStop()
        //viewModel.clearParticipant()
    }
}