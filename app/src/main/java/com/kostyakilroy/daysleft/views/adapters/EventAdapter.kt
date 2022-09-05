package com.kostyakilroy.daysleft.views.adapters

import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.kostyakilroy.daysleft.R
import com.kostyakilroy.daysleft.model.Event
import java.time.*
import java.util.concurrent.TimeUnit

class EventAdapter
    : ListAdapter<Event, EventAdapter.ViewHolder>(EVENT_COMPARATOR) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.item_one_event_item_image)
        private val daysLeft: TextView = view.findViewById(R.id.item_one_event_days_left)
        private val endDate: TextView = view.findViewById(R.id.item_one_event_end_date)
        private val eventName: TextView  = view.findViewById(R.id.item_one_event_event_name)
        val card: MaterialCardView = view.findViewById(R.id.item_one_event_card)

        fun bind(event: Event) {
            val offset = OffsetTime.now().offset.totalSeconds * 1000     // Offset from eventEnd
            val timeLeft = event.eventEnd - Instant.now().toEpochMilli() - offset
            val totalTime = event.eventEnd - event.eventStart

            val hoursTotal = TimeUnit.MILLISECONDS.toHours(totalTime)
            val hoursLeft = TimeUnit.MILLISECONDS.toHours(timeLeft)
            val percentage = if (hoursLeft >= 0) hoursLeft * 100 / hoursTotal else 0
            val daysLeftValue = if (hoursLeft >= 0) hoursLeft / 24 + 1 else 0

            when (percentage) {
                in 0..20 -> image.setImageResource(R.drawable.phase_1)
                in 21..40 -> image.setImageResource(R.drawable.phase_2)
                in 41..60 -> image.setImageResource(R.drawable.phase_3)
                in 61..80 -> image.setImageResource(R.drawable.phase_4)
                else -> image.setImageResource(R.drawable.phase_5)
            }
            daysLeft.text = daysLeftValue.toString()
            endDate.text = format("dd/MM/yyyy", event.eventEnd) // can display irrelevant date
            eventName.text = event.eventName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.importance_one_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.card.setOnClickListener { card ->
            card.findNavController().navigate(R.id.action_mainFragment_to_eventDetailsFragment, bundleOf("eventId" to event.eventId))
        }
    }

    companion object {
        private val EVENT_COMPARATOR = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem.eventId == newItem.eventId
            }

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
                return oldItem == newItem
            }
        }
    }

}