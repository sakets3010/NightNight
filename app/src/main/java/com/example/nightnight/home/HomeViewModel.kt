package com.example.nightnight.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.nightnight.NightRepository
import com.example.nightnight.db.Night
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel @ViewModelInject constructor(
    private val repository: NightRepository
) : ViewModel() {

    private var tonight = MutableLiveData<Night?>()

    val nights = repository.getAllNights()

    private val _navigateToSleepQuality = MutableLiveData<Night>()
    val navigateToSleepQuality: LiveData<Night>
        get() = _navigateToSleepQuality

    init {
        initializeTonight()
    }

    private fun initializeTonight() {
        viewModelScope.launch {
            tonight.value = getNight()
        }
    }

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

            tonight.value = getNight()
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


