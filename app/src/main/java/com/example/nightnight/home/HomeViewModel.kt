package com.example.nightnight.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.nightnight.db.Night
import com.example.nightnight.db.NightRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeViewModel @ViewModelInject constructor(
    private val repository: NightRepository
) : ViewModel() {

    private var _night = MutableLiveData<Night?>()
    val night: LiveData<Night?>
        get() = _night

    private var _navigateTo = MutableLiveData<Night>()
    val navigateTo: LiveData<Night>
        get() = _navigateTo

    val nightList = repository.getAllNights()

    init {
        startTonight()
    }

    private fun startTonight() {
        viewModelScope.launch {
            _night.value = getNight()
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
        _navigateTo.value = null
    }

    fun startTrack() {
        viewModelScope.launch {

            val nextNight = Night()

            insert(nextNight)

            _night.value = getNight()
        }
    }

    fun stopTrack() {
        viewModelScope.launch {

            val previousNight = night.value ?: return@launch

            previousNight.endTime = System.currentTimeMillis()

            update(previousNight)

            _navigateTo.value = previousNight
        }
    }

    val isStartVisible = Transformations.map(_night) {
        null == it
    }

    val isStopVisible = Transformations.map(_night) {
        null != it
    }

}


