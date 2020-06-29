package com.easymove.easymove.shared.modules.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.easymove.easymove.history.*
import com.easymove.easymove.shared.Constants.DATABASE_NAME

@Database(
    entities = [
        History::class,
        HistoryRemoteKeys::class,
        HistoryToBeUploaded::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context) = Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun history(): HistoryDao
    abstract fun historyRemoteKeys(): HistoryRemoteKeysDao
    abstract fun historyToBeUploaded(): HistoryToBeUploadedDao
}