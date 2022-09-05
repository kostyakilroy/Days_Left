package com.kostyakilroy.daysleft.model

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.kostyakilroy.daysleft.model.Event
import com.kostyakilroy.daysleft.model.EventDAO

class EventRepository(private val eventDAO: EventDAO) {

    val basicEvents: LiveData<List<Event>> = eventDAO.getBasicEvents()

    val repeatableEvents: LiveData<List<Event>> = eventDAO.getRepeatableEvents()

    fun getEvent(eventId: Int): LiveData<Event> {
        return eventDAO.getEvent(eventId)
    }

    @WorkerThread
    suspend fun insertEvent(event: Event) {
        eventDAO.insertEvent(event)
    }

    @WorkerThread
    suspend fun updateEvent(event: Event) {
        eventDAO.updateEvent(event)
    }

    @WorkerThread
    suspend fun deleteEvent(event: Event) {
        eventDAO.deleteEvent(event)
    }
}