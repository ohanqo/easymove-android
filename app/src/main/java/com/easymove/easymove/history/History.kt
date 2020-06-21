package com.easymove.easymove.history

data class History(
    val id: Int,
    val price: String,
    val departureStation: String,
    val createdAt: String,
    val userId: String
)