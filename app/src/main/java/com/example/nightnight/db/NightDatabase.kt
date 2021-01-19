package com.example.nightnight.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Night::class], version = 1,exportSchema = false)
abstract class NightDatabase : RoomDatabase() {
    abstract fun nightDao(): NightDao

    companion object {
        @Volatile
        private var INSTANCE: NightDatabase? = null

        fun getInstance(context: Context): NightDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NightDatabase::class.java,
                        "night_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

