package com.easymove.easymove.history

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryRemoteKeys(
    @PrimaryKey
    val id: Int = 1,
    val prevKey: Int?,
    val nextKey: Int?
)