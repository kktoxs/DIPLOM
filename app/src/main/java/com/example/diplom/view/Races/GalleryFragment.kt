package com.example.diplom.view.Races

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diplom.databinding.FragmentGalleryBinding
import com.example.diplom.view.GalleryAdapter
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGalleryBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[RaceViewModel::class.java]
        viewModel.getPreviews(currRaceUID, 0)
        viewModel.previews.observe(viewLifecycleOwner) {
            if (it != null) {
                galleryAdapter.updateList(it.previewURL)
            }
            binding.noPhotos.isVisible = (it == null)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupAdapter() {
        galleryAdapter = GalleryAdapter(requireContext()) {
            viewModel.getPreviews(currRaceUID, it)
        }
        binding.galleryRv.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearPreviews()
    }
}