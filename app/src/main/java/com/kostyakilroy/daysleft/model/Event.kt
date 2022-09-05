package com.kostyakilroy.daysleft.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "event")
data class Event (
    @PrimaryKey(autoGenerate = true)
    val eventId: Int,
    val eventName: String,
    val eventDescription: String?,
    val eventStart: Long,
    val eventEnd: Long,
    val importance: Int,
    val repeating: Int
    )