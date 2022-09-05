package com.kostyakilroy.daysleft.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kostyakilroy.daysleft.model.Event
import com.kostyakilroy.daysleft.model.EventRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: EventRepository) : ViewModel() {

    val basicEventList: LiveData<List<Event>> = repository.basicEvents

    val repeatableEventList: LiveData<List<Event>> = repository.repeatableEvents

    fun deleteEventFromList(event: Event) = viewModelScope.launch {
        repository.deleteEvent(event)
    }
}

class MainViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}