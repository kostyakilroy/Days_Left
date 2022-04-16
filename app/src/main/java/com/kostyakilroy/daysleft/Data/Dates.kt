package com.kostyakilroy.daysleft.Data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity
data class Dates(
    @PrimaryKey(autoGenerate = true)
    val dateId: Int,
    val dateName: String,
    val startDate: Date,
    val endDate: Date
)