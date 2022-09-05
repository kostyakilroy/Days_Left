package com.kostyakilroy.daysleft.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Event::class], version = 1)
abstract class EventDataBase :RoomDatabase() {

    abstract fun eventDAO(): EventDAO

    companion object {
        @Volatile
        private var INSTNANCE: EventDataBase? = null

        fun getDatabase(context: Context): EventDataBase {
            synchronized(this) {
                var instance = INSTNANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventDataBase::class.java,
                        "event_database"
                    ).build()
                    INSTNANCE = instance
                }
                return instance
            }
        }
    }
}