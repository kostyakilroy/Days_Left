package com.kostyakilroy.daysleft.edit

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.kostyakilroy.daysleft.CustomTextView
import com.kostyakilroy.daysleft.Data.DatesDatabase
import com.kostyakilroy.daysleft.R
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.OffsetTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit


class EditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        val dateId = arguments?.getInt("id")


        val application = requireNotNull(this.activity).application
        val dao = DatesDatabase.getDataBase(application).datesDao()
        val data = dao.get(dateId!!)



//        editToolbar.inflateMenu(R.menu.toolbar)
//        editToolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.action_delete -> {
//
//                    view.findNavController().navigate(R.id.action_editFragment_to_mainFragment)
//                    true
//                }
//                else -> false
//            }
//        }


        val moodImage = view.findViewById<ImageView>(R.id.editPageImage)
        val dateName = view.findViewById<CustomTextView>(R.id.editPageTitle)
        val description = view.findViewById<CustomTextView>(R.id.editPageDescription)
        val startDate = view.findViewById<CustomTextView>(R.id.editPageStartDate)
        val endDate = view.findViewById<CustomTextView>(R.id.editPageEndDate)
        val importance = view.findViewById<CustomTextView>(R.id.editPageImportance)
        val dateDate = view.findViewById<TextView>(R.id.date_left)




        data.observe(viewLifecycleOwner, Observer {
            it.let {
                val today = Instant.now()
//                dateDate.text = getString(R.string.days_left, daysLeft)
                val offset = OffsetTime.now().offset.totalSeconds / 3600
                val hoursLeft = ChronoUnit.HOURS.between(today, it.endDate.toInstant()) - offset
                val totalHours = ChronoUnit.HOURS.between(it.startDate.toInstant(), it.endDate.toInstant())
                val percentage = if (hoursLeft >= 0) (hoursLeft * 100) / totalHours else 0
                val daysLeft = if (hoursLeft >= 0) hoursLeft / 24 + 1 else 0

                when (percentage.toInt()) {
                    in 0..20 -> moodImage.setImageResource(R.drawable.phase_1)
                    in 21..40 -> moodImage.setImageResource(R.drawable.phase_2)
                    in 41..60 -> moodImage.setImageResource(R.drawable.phase_3)
                    in 61..80 -> moodImage.setImageResource(R.drawable.phase_4)
                    else -> moodImage.setImageResource(R.drawable.phase_5)
                }

                dateName.setContentText(it.dateName)
                description.setContentText(it.description)
                startDate.setContentText(it.startDate.toString())
                endDate.setContentText(it.endDate.toString())
                importance.setContentText(
                    when (it.importance) {
                        1 -> "Первая"
                        2 -> "Вторая"
                        else -> "Третья"
                    }
                )
            }
        })

        val deleteButton = view.findViewById<MaterialButton>(R.id.editPageDeleteButton)
        deleteButton.setOnClickListener {
            lifecycleScope.launch {
                dao.delete(data.value!!)
            }
            view.findNavController().navigate(R.id.action_editFragment_to_mainFragment)
        }

        return view
    }
}