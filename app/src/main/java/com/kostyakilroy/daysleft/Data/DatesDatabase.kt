package com.kostyakilroy.daysleft.Data

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Dates::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DatesConverters::class)
abstract class DatesDatabase : RoomDatabase() {

    abstract fun datesDao(): DatesDAO


    companion object {
        @Volatile
        private var INSTANCE: DatesDatabase? = null


        @OptIn(InternalCoroutinesApi::class)
        fun getDataBase(context: Context): DatesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatesDatabase::class.java,
                        "dates_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}