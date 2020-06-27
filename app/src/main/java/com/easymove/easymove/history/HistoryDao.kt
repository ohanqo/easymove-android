package com.easymove.easymove.history

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(historyList: List<History>)

    @Query("SELECT * FROM history ORDER BY createdAt DESC")
    fun getAll(): PagingSource<Int, History>

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}