package com.example.diplom.view.Races

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplom.databinding.FragmentParticipantsListBinding


class ParticipantsListFragment : Fragment() {
    private lateinit var binding: FragmentParticipantsListBinding
    private lateinit var viewModel: RaceViewModel
    private lateinit var participantsAdapter: ParticipantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentParticipantsListBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[RaceViewModel::class.java]
        viewModel.participants.observe(viewLifecycleOwner) {
            participantsAdapter.setParticipants(it)
        }
        setupRecycler()
        setupSearchView()
        return binding.root
    }


    private fun setupRecycler() {
        participantsAdapter = ParticipantsAdapter()
        participantsAdapter.onParticipantClickListener = {
            viewModel.selectParticipant(it)
            requireActivity().onBackPressed()
        }
        binding.participantsRv.apply {
            adapter = participantsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val searchResult = viewModel.participants.value?.filter {
                    (it.startNumber?.contains(p0.toString(), ignoreCase = true)
                        ?: false) || (it.fio?.contains(
                        p0.toString(), ignoreCase = true
                    ) ?: false)

                } ?: listOf()
                participantsAdapter.setParticipants(searchResult)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        binding.searchView.setOnCloseListener {
            participantsAdapter.setParticipants(viewModel.participants.value ?: listOf())
            false
        }
    }
}