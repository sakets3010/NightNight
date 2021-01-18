package com.example.nightnight.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Night(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0L,
    @ColumnInfo
    var initTime: Long = System.currentTimeMillis(),
    @ColumnInfo
    var endTime: Long = initTime,
    @ColumnInfo
    var sleepRating: Int = 0,
)


