package com.example.nightnight.home

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.nightnight.NightRepository
import com.example.nightnight.db.Night
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel @ViewModelInject constructor(
    private val repository: NightRepository
) : ViewModel() {

    private var _tonight = MutableLiveData<Night?>()
    val tonight: LiveData<Night?>
        get() = _tonight
    private lateinit var _night :Night

    val nights = repository.getAllNights()

    private var _navigateToSleepQuality = MutableLiveData<Night>()
    val navigateToSleepQuality: LiveData<Night>
        get() = _navigateToSleepQuality

    private var _timeDifference = MutableLiveData<Long>()
    val timeDifference: LiveData<Long>
        get() = _timeDifference

    init {
        initializeTonight()
    }


    private fun initializeTonight() {
        viewModelScope.launch {
            _tonight.value = getNight()
            //getTimeDifference()
            Log.d("differ", "func called")
        }
    }

//    private suspend fun getTimeDifference () {
//        _timeDifference.value = tonight.value?.initTime
//    }

    private suspend fun getNight(): Night? {
        var night = repository.getLatestNight()
        if (night?.endTime != night?.initTime) {
            night = null
        }
        return night
    }


    private suspend fun update(night: Night) {
        withContext(Dispatchers.IO) {
            repository.updateNight(night)
        }
    }

    val startButtonVisible = Transformations.map(tonight) {
        null == it
    }

    val stopButtonVisible = Transformations.map(tonight) {
        null != it
    }

    private suspend fun insert(night: Night) {
        withContext(Dispatchers.IO) {
            repository.addNight(night)
        }
    }

    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    fun startTrack() {
        viewModelScope.launch {

            val newNight = Night()

            insert(newNight)

            _tonight.value = getNight()
        }
    }

    fun stopTrack() {
        viewModelScope.launch {

            val oldNight = tonight.value ?: return@launch

            oldNight.endTime = System.currentTimeMillis()

            update(oldNight)

            _navigateToSleepQuality.value = oldNight
        }
    }

}


