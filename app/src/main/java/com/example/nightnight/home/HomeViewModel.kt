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
        initializeTonight()
    }

    private fun initializeTonight() {
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

            val newNight = Night()

            insert(newNight)

            _night.value = getNight()
        }
    }

    fun stopTrack() {
        viewModelScope.launch {

            val oldNight = night.value ?: return@launch

            oldNight.endTime = System.currentTimeMillis()

            update(oldNight)

            _navigateTo.value = oldNight
        }
    }

    val isStartVisible = Transformations.map(_night) {
        null == it
    }

    val isStopVisible = Transformations.map(_night) {
        null != it
    }

}


