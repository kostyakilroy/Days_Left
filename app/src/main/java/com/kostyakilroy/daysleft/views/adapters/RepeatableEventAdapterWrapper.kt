package com.kostyakilroy.daysleft.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kostyakilroy.daysleft.R

class RepeatableEventAdapterWrapper(private val context: Context, private val adapter: RepeatableEventAdapter)
    : RecyclerView.Adapter<RepeatableEventAdapterWrapper.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(adapter: RepeatableEventAdapter) {
            val rv = itemView.findViewById<RecyclerView>(R.id.repeatable_event_item_wrapper)
            rv.adapter = adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.repeateable_events_item_wrapper, parent, false)
        val rv = view.findViewById<RecyclerView>(R.id.repeatable_event_item_wrapper)
        rv.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(adapter)
    }

    override fun getItemCount(): Int = 1
}