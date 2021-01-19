package com.example.nightnight

import androidx.lifecycle.LiveData
import com.example.nightnight.db.Night
import com.example.nightnight.db.NightDao
import javax.inject.Inject


class NightRepository @Inject constructor(private val nightDao: NightDao) {

    suspend fun addNight(night: Night) = nightDao.add(night)

    suspend fun updateNight(night: Night) = nightDao.update(night)

    suspend fun getSpecificNight(key: Long) = nightDao.getData(key)

    suspend fun getLatestNight() = nightDao.getLastNight()

    fun getAllNights(): LiveData<List<Night>> = nightDao.getAllEntries()

}