package com.easymove.easymove.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryToBeUploadedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(history: HistoryToBeUploaded)

    @Query("SELECT * FROM historytobeuploaded")
    fun getAll(): List<HistoryToBeUploaded>

    @Query("DELETE FROM historytobeuploaded")
    suspend fun deleteAll()
}