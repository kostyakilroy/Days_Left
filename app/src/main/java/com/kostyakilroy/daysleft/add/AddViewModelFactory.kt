package com.kostyakilroy.daysleft.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kostyakilroy.daysleft.Data.DatesDAO

class AddViewModelFactory(private val datesDAO: DatesDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            return AddViewModel(datesDAO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}