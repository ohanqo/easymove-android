package com.easymove.easymove.history

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.easymove.easymove.shared.Constants.LOGGER_TAG
import com.haroldadmin.cnradapter.NetworkResponse
import org.koin.core.KoinComponent
import org.koin.core.inject

class HistoryToBeUploadedWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters), KoinComponent {

    private val historyDao: HistoryDao by inject()
    private val service: HistoryService by inject()
    private val toUploadDao: HistoryToBeUploadedDao by inject()

    override suspend fun doWork(): Result {
        val historyListToUpload = toUploadDao.getAll()

        Log.d(LOGGER_TAG, "worker try to upload ${historyListToUpload.size} items")
        when (val response = service.create(historyListToUpload)) {
            is NetworkResponse.Success -> {
                Log.d(LOGGER_TAG, "worker upload success")
                historyDao.insertAll(response.body)
                toUploadDao.deleteAll()
            }
            else -> {
                Log.d(LOGGER_TAG, "worker upload failed, retry later")
                return Result.retry()
            }
        }

        return Result.success()
    }
}