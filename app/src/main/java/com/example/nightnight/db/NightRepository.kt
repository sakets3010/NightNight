package com.example.nightnight.db

import androidx.lifecycle.LiveData
import javax.inject.Inject


class NightRepository @Inject constructor(private val nightDao: NightDao) {

    suspend fun addNight(night: Night) = nightDao.add(night)

    suspend fun updateNight(night: Night) = nightDao.update(night)

    suspend fun getSpecificNight(key: Long) = nightDao.getData(key)

    suspend fun getLatestNight() = nightDao.getLastNight()

    fun getAllNights(): LiveData<List<Night>> = nightDao.getAllEntries()

}