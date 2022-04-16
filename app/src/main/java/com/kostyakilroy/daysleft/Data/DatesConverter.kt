package com.kostyakilroy.daysleft.Data

import androidx.room.TypeConverter
import java.util.*

class DatesConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}