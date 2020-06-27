package com.easymove.easymove.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: HistoryRemoteKeys)

    @Query("SELECT * FROM historyremotekeys WHERE id = 1")
    suspend fun get(): HistoryRemoteKeys?

    @Query("DELETE FROM historyremotekeys")
    suspend fun delete()
}