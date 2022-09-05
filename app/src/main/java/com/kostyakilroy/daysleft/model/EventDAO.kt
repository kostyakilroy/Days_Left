package com.kostyakilroy.daysleft.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kostyakilroy.daysleft.model.Event

@Dao
interface EventDAO {

    @Query("SELECT * FROM event WHERE repeating > 0 ORDER BY eventId DESC")
    fun getRepeatableEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE repeating == 0 ORDER BY eventId DESC")
    fun getBasicEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM event WHERE eventId = :eventId")
    fun getEvent(eventId: Int): LiveData<Event>

    @Insert
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)
}