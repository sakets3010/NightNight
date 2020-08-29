package com.example.nightnight.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nightnight.helper.Sleep
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job


class HomeViewModel : ViewModel() {

    private val viewModelJob = Job()

    lateinit var docId:String

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private var nightList = mutableListOf<Sleep>()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid
    private val db = Firebase.firestore


    fun onStarted() {
        Log.d("saved","onStarted called from the vm")
        db.collection("Users").document(uid!!).collection("sleepData").add(Sleep()).addOnSuccessListener {
                    documentReference ->
                Log.d("saved","data added with id ${documentReference.id}")
                docId = documentReference.id
            }
    }

    fun onStopped() {
        db.collection("Users").document(uid!!).collection("sleepData").document(docId).update(
                    mapOf(
                        "endTime" to System.currentTimeMillis()
                    )
                )
        Log.d("saved","data updated having id $docId")
    }
    private val _savedNights: MutableLiveData<List<Sleep>> = MutableLiveData()
    fun signUserOut() {
        Firebase.auth.signOut()
    }
    fun onDone():LiveData<List<Sleep>> {
        db.collection("Users").document(uid!!).collection("sleepData").whereEqualTo("updated",true).addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.d("HomeViewmodel", "Listen failed.", e)
                    return@addSnapshotListener
                }
                if (snapshots != null) {
                    for(snapshot in snapshots){
                        val sleep = snapshot.toObject(Sleep::class.java)
                        nightList.add(sleep)
                    }
                } else {
                    Log.d("HomeViewmodel", "Current data: null")
                }
                _savedNights.value = nightList
            }
        return _savedNights
    }
}


