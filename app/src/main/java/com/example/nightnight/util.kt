package com.example.nightnight

import android.content.res.Resources
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

fun convertDurationToFormatted(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
    val durationMilli = endTimeMilli - startTimeMilli
    return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            "$seconds second(s)"
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MILLISECONDS)
            val seconds = TimeUnit.SECONDS.convert(durationMilli % ONE_MINUTE_MILLIS, TimeUnit.MILLISECONDS)
            "$minutes minute(s) $seconds second(s)"
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.MILLISECONDS)
            val minutes = TimeUnit.MINUTES.convert(durationMilli % ONE_HOUR_MILLIS, TimeUnit.MILLISECONDS)
            "$hours hour(s) $minutes minute(s)"
        }
    }
}