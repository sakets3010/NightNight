package com.example.nightnight.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Night::class], version = 1)
abstract class NightDatabase : RoomDatabase() {
    abstract fun nightDao(): NightDao
 }

