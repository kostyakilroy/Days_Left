package com.kostyakilroy.daysleft.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kostyakilroy.daysleft.R
import com.kostyakilroy.daysleft.model.Event
import java.lang.Integer.min
import java.time.Instant
import java.time.OffsetTime
import java.util.concurrent.TimeUnit

class RepeatableEventAdapter
    : ListAdapter<Event, RepeatableEventAdapter.ViewHolder>(EVENT_COMPARATOR) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val daysLeft: TextView
        val eventName: TextView

        init {
            image = view.findViewById(R.id.repeatable_event_item_image)
            daysLeft = view.findViewById(R.id.repeatable_event_days_left)
            eventName = view.findViewById(R.id.repeatable_event_event_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repeatable_events_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current = getItem(position)
        val offset = OffsetTime.now().offset.totalSeconds * 1000     // Offset from eventEnd
        val timeLeft = current.eventEnd - Instant.now().toEpochMilli() - offset
        val totalTime = current.eventEnd - current.eventStart

        val hoursTotal = TimeUnit.MILLISECONDS.toHours(totalTime)
        val hoursLeft = TimeUnit.MILLISECONDS.toHours(timeLeft)
        val percentage = if (hoursLeft >= 0) hoursLeft * 100 / hoursTotal else 0
        val daysLeftValue = if (hoursLeft >= 0) hoursLeft / 24 + 1 else 0

        holder.apply {
            when (percentage) {
                in 0..20 -> image.setImageResource(R.drawable.phase_1)
                in 21..40 -> image.setImageResource(R.drawable.phase_2)
                in 41..60 -> image.setImageResource(R.drawable.phase_3)
                in 61..80 -> image.setImageResource(R.drawable.phase_4)
                else -> image.setImageResource(R.drawable.phase_5)
            }
            daysLeft.text = daysLeftValue.toString()
            eventName.text = current.eventName
        }
    }

    override fun getItemCount(): Int = min(currentList.size, 5)

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