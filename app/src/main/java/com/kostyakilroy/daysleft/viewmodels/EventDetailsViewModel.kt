package com.kostyakilroy.daysleft.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kostyakilroy.daysleft.model.Event
import com.kostyakilroy.daysleft.model.EventRepository
import kotlinx.coroutines.launch

class EventDetailsViewModel(private val repository: EventRepository) : ViewModel() {

    fun getEvent(eventId: Int): LiveData<Event> {
        return repository.getEvent(eventId)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch {
        repository.deleteEvent(event)
    }

}

class EventDetailsViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}