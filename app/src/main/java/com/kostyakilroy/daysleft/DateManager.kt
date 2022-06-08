package com.kostyakilroy.daysleft

import com.kostyakilroy.daysleft.Data.Dates
import java.time.Instant
import java.time.OffsetTime
import java.time.temporal.ChronoUnit

class DateManager {

    fun daysLeft(date: Dates): Long {
        val today = Instant.now()
        val offset = OffsetTime.now().offset.totalSeconds / 3600
        val hoursLeft = ChronoUnit.HOURS.between(today, date.endDate.toInstant()) - offset
        return if (hoursLeft >= 0) hoursLeft / 24 + 1 else 0
    }
}