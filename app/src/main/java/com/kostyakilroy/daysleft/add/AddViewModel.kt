package com.kostyakilroy.daysleft.add

import android.app.DatePickerDialog
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.kostyakilroy.daysleft.Data.Dates
import com.kostyakilroy.daysleft.Data.DatesDAO
import com.kostyakilroy.daysleft.Data.DatesDatabase
import com.kostyakilroy.daysleft.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(val datesDAO: DatesDAO) : ViewModel() {


    var selectedDate: Date = Calendar.getInstance().time
    var cal = 0L

//    val dateSetListener = DatePickerDialog
//        .OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
//            cal.set(Calendar.YEAR, year)
//            cal.set(Calendar.MONTH, monthOfYear)
//            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//
//            val myFormat = "dd.MM.yyyy"
//            val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
//
//        }

//    dateButton.setOnClickListener {
//        DatePickerDialog(
//            requireActivity(), dateSetListener,
//            cal.get(Calendar.YEAR),
//            cal.get(Calendar.MONTH),
//            cal.get(Calendar.DAY_OF_MONTH)).show()
//    }

//    fun addDateToDatabase(dateName: Editable) {
//        viewModelScope.launch {
//            val date = Dates(
//                0,
//                dateName.toString(),
//                Calendar.getInstance(TimeZone.getTimeZone("UTC")).time,
//                cal
//            )
//            datesDAO.insert(date)
//        }
//    }
}