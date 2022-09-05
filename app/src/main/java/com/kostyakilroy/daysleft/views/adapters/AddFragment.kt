
package com.kostyakilroy.daysleft.views.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.kostyakilroy.daysleft.R
import com.kostyakilroy.daysleft.databinding.FragmentAddBinding
import com.kostyakilroy.daysleft.model.Event
import com.kostyakilroy.daysleft.model.EventDataBase
import com.kostyakilroy.daysleft.model.EventRepository
import com.kostyakilroy.daysleft.viewmodels.AddViewModel
import com.kostyakilroy.daysleft.viewmodels.AddViewModelFactory
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private const val FORMAT = "dd.MM.yyyy"

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view =  binding.root

        val application = requireNotNull(this.activity).application
        val dataBase = EventDataBase.getDatabase(application)
        val repository = EventRepository(dataBase.eventDAO())
        val factory = AddViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[AddViewModel::class.java]

        nameFocusListener()
        dateFocusListener()

        val dateConstraints =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите дату события")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(dateConstraints.build())
                .build()

        binding.addTextInputLayoutDate.setEndIconOnClickListener {
            datePicker.show(this@AddFragment.parentFragmentManager, "DialogDate")
        }

        datePicker.addOnPositiveButtonClickListener {
            val formatter = DateTimeFormatter.ofPattern(FORMAT)
            val instantSelection = Instant.ofEpochMilli(datePicker.selection ?: Instant.now().toEpochMilli())
            val date = LocalDateTime.ofInstant(instantSelection, ZoneId.systemDefault())
            val formattedDate = date.format(formatter)

            binding.addEdittextDate.setText(formattedDate)

        }

        binding.addAddButton.setOnClickListener {
            submitDate()
            if (binding.addTextInputLayoutName.error == null && binding.addTextInputLayoutDate.error == null) {
                val event = Event(
                    0,
                    binding.addEdittextName.text.toString(),
                    binding.addEdittextDescription.text.toString(),
                    Instant.now().toEpochMilli(),
                    datePicker.selection ?: Instant.now().toEpochMilli(),
                    binding.addSpinnerImportance.selectedItemPosition,
                    binding.addSpinnerRepeating.selectedItemPosition
                )
                viewModel.addEventToDatabase(event)
                view.findNavController().navigate(R.id.action_addFragment_to_mainFragment)
            }

        }

        return view
    }

    private fun submitDate() {
        binding.addTextInputLayoutName.error = validName()
        binding.addTextInputLayoutDate.error = validDate()
    }


    private fun nameFocusListener() {
        binding.addEdittextName.setOnFocusChangeListener { _, focused ->
            if(!focused) binding.addTextInputLayoutName.error = validName()
        }
    }

    private fun validName(): String? {
        val nameText = binding.addEdittextName.text.toString()
        if(nameText.isEmpty()) return "Введите название события"
        return null
    }

    private fun dateFocusListener() {
        binding.addEdittextDate.setOnFocusChangeListener { _, focused ->
            if(!focused) binding.addTextInputLayoutDate.error = validDate()
        }
    }

    private fun validDate(): String? {
        val dateText = binding.addEdittextDate.text.toString()
        val today = Calendar.getInstance().time



        when {
            dateText.isEmpty() -> {
                binding.addTextInputLayoutDate.errorIconDrawable = null
                return "Введите дату события"
            }
            !dateText.matches("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.\\d{4}".toRegex()) -> {
                binding.addTextInputLayoutDate.errorIconDrawable = null
                return "Дата должна быть формата ДД.ММ.ГГГГ"
            }

        }
        val sdf = SimpleDateFormat(FORMAT).parse(dateText)
        if (sdf < today) {
            binding.addTextInputLayoutDate.errorIconDrawable = null
            return "Нельзя вернуться в прошлое"
        }

        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}