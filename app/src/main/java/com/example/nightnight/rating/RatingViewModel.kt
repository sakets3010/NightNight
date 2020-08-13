package com.example.nightnight.rating

import androidx.lifecycle.ViewModel
import com.example.nightnight.home.HomeViewModel.Util.Companion._navigationDone
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RatingViewModel : ViewModel() {

    private val viewModelJob = Job()

    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val db = Firebase.firestore
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun updateRating(rating:Int,docId:String){

        db.collection("Users").document(uid!!).collection("sleepData").document(docId).update(
                mapOf(
                    "sleepRating" to rating
                )
            )

            _navigationDone.value = true

    }
}