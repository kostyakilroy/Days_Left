package com.kostyakilroy.daysleft

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kostyakilroy.daysleft.Data.DatesDatabase

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview)

        val application = requireNotNull(this.activity).application
        val dao = DatesDatabase.getDataBase(application).datesDao()
        val data = dao.getAll()
        val adapter = DatesListAdapter()

        data.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })



        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(application)

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_mainFragment_to_addFragment)
        }
        return view
    }

}