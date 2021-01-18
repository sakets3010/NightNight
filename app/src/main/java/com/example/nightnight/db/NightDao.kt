package com.example.nightnight.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NightDao {

    @Insert
    suspend fun add(night: Night)

    @Update
    suspend fun update(night: Night)

    @Query("SELECT * from Night WHERE id = :key")
    suspend fun getData(key: Long): Night?

    @Query("SELECT * FROM Night ORDER BY id DESC LIMIT 1")
    suspend fun getLastNight(): Night?


    @Query("SELECT * FROM Night ORDER BY id DESC")
    fun getAllEntries(): LiveData<List<Night>>

}

