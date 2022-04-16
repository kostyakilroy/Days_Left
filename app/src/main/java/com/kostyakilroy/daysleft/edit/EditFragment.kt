package com.kostyakilroy.daysleft.edit

import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kostyakilroy.daysleft.Data.DatesDatabase
import com.kostyakilroy.daysleft.R
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

        val imageBG = view.findViewById<ImageView>(R.id.imageView)


        val dateName = view.findViewById<TextView>(R.id.date_name)
        val dateDate = view.findViewById<TextView>(R.id.date_date)


        data.observe(viewLifecycleOwner, Observer {
            it.let {
                dateName.text = it.dateName
                val today = Calendar.getInstance().time
                val offsetDateTime = TimeZone.getDefault().getOffset(today.time)
                val date = it.endDate.time - today.time - offsetDateTime.toLong()
                val daysLeft = TimeUnit.MILLISECONDS.toDays(date)
                dateDate.text = getString(R.string.days_left, daysLeft)
            }
        })

        return view
    }
}