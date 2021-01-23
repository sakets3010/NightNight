package com.example.nightnight.monitor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.nightnight.db.NightRepository
import java.text.SimpleDateFormat
import java.util.*

class GraphViewmodel  @ViewModelInject constructor(
    repository: NightRepository
) : ViewModel() {

    val nights  = repository.getAllNights()

    fun getDateTime(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("dd-MMM-yy", Locale.getDefault())
            sdf.format(Date(s))
        } catch (e: Exception) {
           throw Exception (e.toString())
        }
    }


}