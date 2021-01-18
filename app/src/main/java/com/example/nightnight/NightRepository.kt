package com.example.nightnight

import androidx.lifecycle.LiveData
import com.example.nightnight.db.Night
import com.example.nightnight.db.NightDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


class NightRepository @Inject constructor(private val nightDao: NightDao) {


    fun addNight(night: Night) {
        CoroutineScope(IO).launch {
            nightDao.add(night)
        }
    }

    fun updateNight(night: Night) {
        CoroutineScope(IO).launch {
            nightDao.update(night)
        }
    }

    fun getSpecificNight(key:Long) = nightDao.getData(key)

    fun getLatestNight() = nightDao.getLastNight()

    fun getAllNights(): LiveData<List<Night>> = nightDao.getAllEntries()


}