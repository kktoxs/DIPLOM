package com.example.diplom.view.Races

import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplom.databinding.FragmentMetaBinding


class MetaFragment : Fragment() {
    private lateinit var binding: FragmentMetaBinding
    private lateinit var viewModel: RaceViewModel
    private lateinit var metaAdapter: MetaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMetaBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[RaceViewModel::class.java]
        viewModel.currentPhoto.observe(viewLifecycleOwner) {
            //viewModel.getMeta()
            Glide.with(requireActivity())
                .load("https://photo.marshalone.ru/api/photo/file/get?type=resized&UUID=$it")
                .into(binding.metaIv)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentMeta.observe(viewLifecycleOwner) {
            metaAdapter.submitList(it.competitors)
            Log.d("metas", it.competitors.toString())
        }
        setupRecycler()
    }

    private fun setupRecycler() {
        metaAdapter = MetaAdapter()
        binding.metaRv.apply {
            adapter = metaAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}