package com.kostyakilroy.daysleft.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kostyakilroy.daysleft.model.Event
import com.kostyakilroy.daysleft.model.EventRepository
import kotlinx.coroutines.launch

class AddViewModel(private val repository: EventRepository) : ViewModel() {

    fun addEventToDatabase(event: Event) = viewModelScope.launch {
        repository.insertEvent(event)
    }
}

class AddViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}