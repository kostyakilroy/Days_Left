package com.kostyakilroy.daysleft.views.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.kostyakilroy.daysleft.R
import com.kostyakilroy.daysleft.databinding.FragmentMainBinding
import com.kostyakilroy.daysleft.model.EventDataBase
import com.kostyakilroy.daysleft.model.EventRepository
import com.kostyakilroy.daysleft.viewmodels.MainViewModel
import com.kostyakilroy.daysleft.viewmodels.MainViewModelFactory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        //TODO in correct way?
        val dataBase = EventDataBase.getDatabase(requireContext())
        val repository = EventRepository(dataBase.eventDAO())
        val factory = MainViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]


        val basicEventAdapter = EventAdapter()
        val repeatableEventAdapter = RepeatableEventAdapter()
        val repeatableEventAdapterWrapper = RepeatableEventAdapterWrapper(requireContext(), repeatableEventAdapter)

        viewModel.basicEventList.observe(viewLifecycleOwner, Observer {
            it?.let { basicEventAdapter.submitList(it) }
        })

        viewModel.basicEventList.observe(viewLifecycleOwner, Observer {
            it?.let { repeatableEventAdapter.submitList(it) }
        })

        val concatAdapter = ConcatAdapter(repeatableEventAdapterWrapper, basicEventAdapter)

        binding.mainRecyclerView.adapter = concatAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.fab.setOnClickListener { fab ->
            fab.findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}