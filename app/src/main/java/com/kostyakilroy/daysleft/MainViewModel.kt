package com.kostyakilroy.daysleft

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kostyakilroy.daysleft.Data.Dates
import com.kostyakilroy.daysleft.Data.DatesDAO
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(private val dao: DatesDAO) : ViewModel() {

    val allDates: LiveData<List<Dates>> = dao.getAll()

    fun insert(date: Dates) = viewModelScope.launch {
        dao.insert(date)
    }

}

class MainViewModelFactory(private val dao: DatesDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}