package com.easymove.easymove.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey
    val id: Int,
    val price: String,
    val departureStation: String,
    val createdAt: String
)