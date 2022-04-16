package com.kostyakilroy.daysleft.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.kostyakilroy.daysleft.Data.Dates
import com.kostyakilroy.daysleft.Data.DatesDatabase
import com.kostyakilroy.daysleft.R
import com.kostyakilroy.daysleft.databinding.FragmentAddBinding
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val FORMAT = "dd.MM.yyyy"

@InternalCoroutinesApi
class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AddViewModel
    private lateinit var viewModelFactory: AddViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view =  binding.root

        val application = requireNotNull(this.activity).application
        val dao = DatesDatabase.getDataBase(application).datesDao()

        viewModelFactory = AddViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddViewModel::class.java)


//        setDatePicker = view.findViewById(R.id.select_date_field)
//        editDatePicker = view.findViewById(R.id.edit_date_field)
//
//        val dateEvent: TextInputEditText = view.findViewById(R.id.add_date_name)
//
//
//
//
//        val datePicker =
//            MaterialDatePicker.Builder.datePicker()
//                .setTitleText("Select date")
//                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//                .build()
//
//        setDatePicker.setEndIconOnClickListener {
//
//
//            datePicker.show(this@AddFragment.parentFragmentManager, "DialogDate")
//
//
//        }
//
//        datePicker.addOnPositiveButtonClickListener {
//            val myFormat = "dd.MM.yyyy"
//            val sdf = SimpleDateFormat(myFormat, Locale.ROOT)
//            val date = sdf.format(Date(datePicker.selection!!))
//            editDatePicker.setText(date)
//            viewModel.cal = datePicker.selection!!
//
//        }
//
//        setDatePicker.editText?.addTextChangedListener(object : TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                setDatePicker.error = null
//                setDatePicker.isErrorEnabled = false
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                setDatePicker.error = null
//                setDatePicker.isErrorEnabled = false
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                setDatePicker.error = null
//                setDatePicker.isErrorEnabled = false
//            }
//
//        })
//
//        val addButton: Button = view.findViewById(R.id.add_date_button)
//        addButton.setOnClickListener {
//            when {
//                dateEvent.text.toString().isEmpty()-> {
//                    dateEvent.error = "Please Enter Date Event name"
//                }
//                editDatePicker.text.toString().isEmpty()-> {
//                    setDatePicker.setError("Plese chose a data")
//                }
//                else -> {
//                    viewModel.addDateToDatabase(dateEvent.text!!)
//                }
//            }
//        }

        nameFocusListener()
        dateFocusListener()

        val dateConstraints =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(dateConstraints.build())
                .build()

        binding.dateFieldLayout.setEndIconOnClickListener {
            datePicker.show(this@AddFragment.parentFragmentManager, "DialogDate")
        }

        datePicker.addOnPositiveButtonClickListener {
            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(FORMAT, Locale.ROOT)
            val date = sdf.format(Date(datePicker.selection!!))
            binding.dateFieldEdit.setText(date)

        }

        binding.addButton.setOnClickListener {
            submitDate()
            if (binding.dateFieldLayout.error == null && binding.nameFieldLayout.error == null) {
                lifecycleScope.launch {
                    val date = Dates(
                        0,
                        binding.nameFieldEdit.text.toString(),
                        Calendar.getInstance(Locale.ROOT).time,
                        Date(datePicker.selection!!)
                    )
                    dao.insert(date)
                    view.findNavController().navigate(R.id.action_addFragment_to_mainFragment)
                }
            }
        }

        return view
    }


    private fun submitDate() {
        binding.nameFieldLayout.error = validName()
        binding.dateFieldLayout.error = validDate()


    }


    private fun nameFocusListener()
    {
        binding.nameFieldEdit.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.nameFieldLayout.error = validName()
            }
        }
    }

    private fun validName(): String?
    {
        val nameText = binding.nameFieldEdit.text.toString()
        if(nameText.isEmpty())
        {
            return "Введите название события"
        }
        return null
    }

    private fun dateFocusListener()
    {
        binding.dateFieldEdit.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.dateFieldLayout.error = validDate()
            }
        }
    }

    private fun validDate(): String?
    {
        val dateText = binding.dateFieldEdit.text.toString()
        val today = Calendar.getInstance().time

        if(dateText.isEmpty())
        {
            binding.dateFieldLayout.errorIconDrawable = null
            return "Введите дату события"
        }
        if (!dateText.matches("(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.\\d{4}".toRegex())){
            binding.dateFieldLayout.errorIconDrawable = null
            return "Дата должна быть формата ДД.ММ.ГГГГ"
        }
        val sdf = SimpleDateFormat(FORMAT).parse(dateText)
        if (sdf < today) {
            binding.dateFieldLayout.errorIconDrawable = null
            return "Нельзя вернуться в прошлое"
        }
        return null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}