package com.example.nightnight.monitor

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.nightnight.NightRepository
import com.example.nightnight.R
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.text.SimpleDateFormat
import java.util.*

class GraphViewmodel  @ViewModelInject constructor(
    private val repository: NightRepository
) : ViewModel() {

    val nights  = repository.getAllNights()

    fun getDateTime(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("dd-MMM-yy", Locale.getDefault())
            val netDate = Date(s)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }


}