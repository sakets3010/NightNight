package com.example.nightnight.helper


data class Sleep(var startTime:Long = System.currentTimeMillis(),var endTime:Long = startTime,var sleepRating:Int = 0)
data class SleepWrapper(var docId:String,var sleep:Sleep)