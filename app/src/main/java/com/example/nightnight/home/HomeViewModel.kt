package com.example.nightnight.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.hilt.lifecycle.ViewModelInject
import com.example.nightnight.NightRepository
import com.example.nightnight.db.Night
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job


class HomeViewModel @ViewModelInject constructor(
    private val repository: NightRepository
) : ViewModel() {

    private val viewModelJob = Job()

    lateinit var docId:String

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private var nightList = mutableListOf<Night>()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val db = Firebase.firestore


    fun onStarted() {


    }

    fun onStopped() {

    }

    private val _savedNights: MutableLiveData<List<Night>> = MutableLiveData()

    fun onDone() {


    }
}


