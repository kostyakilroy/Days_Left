package com.kostyakilroy.daysleft.views.adapters

import android.os.Bundle
import android.text.format.DateFormat.format
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.kostyakilroy.daysleft.R
import com.kostyakilroy.daysleft.databinding.FragmentEventDetailsBinding
import com.kostyakilroy.daysleft.model.EventDataBase
import com.kostyakilroy.daysleft.model.EventRepository
import com.kostyakilroy.daysleft.viewmodels.EventDetailsViewModel
import com.kostyakilroy.daysleft.viewmodels.EventDetailsViewModelFactory
import java.time.Instant
import java.time.OffsetTime
import java.util.concurrent.TimeUnit

class EventDetailsFragment : Fragment() {

    private var _binding: FragmentEventDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val application = requireNotNull(this.activity).application
        val dataBase = EventDataBase.getDatabase(application)
        val repository = EventRepository(dataBase.eventDAO())
        val factory = EventDetailsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[EventDetailsViewModel::class.java]

        val eventId = arguments?.getInt("eventId") ?: 0
        val eventLiveData = viewModel.getEvent(eventId)

        eventLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { event ->
                val offset = OffsetTime.now().offset.totalSeconds * 1000     // Offset from eventEnd
                val timeLeft = event.eventEnd - Instant.now().toEpochMilli() - offset
                val totalTime = event.eventEnd - event.eventStart

                val hoursTotal = TimeUnit.MILLISECONDS.toHours(totalTime)
                val hoursLeft = TimeUnit.MILLISECONDS.toHours(timeLeft)
                val percentage = if (hoursLeft >= 0) hoursLeft * 100 / hoursTotal else 0
                val daysLeftValue = if (hoursLeft >= 0) hoursLeft / 24 + 1 else 0

                when (percentage) {
                    in 0..20 -> binding.eventDetailsImage.setImageResource(R.drawable.phase_1)
                    in 21..40 -> binding.eventDetailsImage.setImageResource(R.drawable.phase_2)
                    in 41..60 -> binding.eventDetailsImage.setImageResource(R.drawable.phase_3)
                    in 61..80 -> binding.eventDetailsImage.setImageResource(R.drawable.phase_4)
                    else -> binding.eventDetailsImage.setImageResource(R.drawable.phase_5)
                }

                binding.apply {
                    eventDetailsName.setContentText(event.eventName)
                    if (event.eventDescription.isNullOrEmpty())
                        binding.eventDetailsDescription.visibility = View.GONE else {
                        eventDetailsDescription.setContentText(event.eventDescription)
                    }
                    eventDetailsEventStart.setContentText(format("dd/MM/yyyy", event.eventStart) as String?)
                    eventDetailsEventEnd.setContentText(format("dd/MM/yyyy", event.eventEnd) as String?)
                    eventDetailsEventImportance.setContentText(
                        when (event.importance) {
                            0 -> "Первая"
                            1 -> "Вторая"
                            else -> "Третья"
                        }
                    )
                }
            }
        })

        binding.eventDetailsDeleteButton.setOnClickListener {
            viewModel.deleteEvent(eventLiveData.value!!)
            view.findNavController().navigate(R.id.action_eventDetailsFragment_to_mainFragment)
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}