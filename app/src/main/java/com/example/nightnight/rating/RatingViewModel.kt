package com.example.nightnight.rating

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nightnight.db.NightRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RatingViewModel @ViewModelInject constructor(
    private val repository: NightRepository
) : ViewModel() {


    fun updateRating(quality: Int, key: Long) {
        viewModelScope.launch {
            val tonight = repository.getSpecificNight(key)
            if (tonight != null) {
                tonight.sleepRating = quality
                repository.updateNight(tonight)
            }
        }
    }


}