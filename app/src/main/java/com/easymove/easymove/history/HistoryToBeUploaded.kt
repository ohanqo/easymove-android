package com.easymove.easymove.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryToBeUploaded(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val price: String,
    val departureStation: String
)