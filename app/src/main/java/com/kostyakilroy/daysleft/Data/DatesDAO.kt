package com.kostyakilroy.daysleft.Data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DatesDAO {
    @Query("SELECT * FROM dates WHERE dateId = :dateId")
    fun get(dateId: Int): LiveData<Dates>

    @Query("SELECT * FROM dates ORDER BY dateId DESC")
    fun getAll(): LiveData<List<Dates>>

    @Insert
    suspend fun insert(date: Dates)

    @Update
    suspend fun update(date: Dates)

    @Delete
    suspend fun delete(date: Dates)
}