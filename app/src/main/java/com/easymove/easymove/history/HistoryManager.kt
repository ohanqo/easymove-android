package com.easymove.easymove.history

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.easymove.easymove.shared.Constants.LOGGER_TAG
import com.easymove.easymove.shared.modules.network.ConnectivityObserver
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.altbeacon.beacon.Region


class HistoryManager(
    private val historyService: HistoryService,
    private val connectivity: ConnectivityObserver,
    private val historyDao: HistoryDao,
    private val historyToBeUploadedDao: HistoryToBeUploadedDao,
    private val context: Context
) {
    var isInTransaction = false
    var region: Region? = null

    fun storeByRegion(region: Region) {
        if (!isInTransaction && this.region?.uniqueId != region.uniqueId) {
            this.region = region
            GlobalScope.launch { store(region) }
        }
    }

    private suspend fun store(region: Region) {
        isInTransaction = true

        Log.d(LOGGER_TAG, "store")
        val dto = HistoryToBeUploaded(0, "Navigo", region.uniqueId)

        if (connectivity.hasInternet()) {
            Log.d(LOGGER_TAG, "has internet, upload directly to the API")
            upload(dto)
        } else {
            Log.d(LOGGER_TAG, "is offline, store locally to be uploaded later")
            storeLocally(dto)
        }

        isInTransaction = false
    }

    private suspend fun upload(dto: HistoryToBeUploaded) {
        when (val response = historyService.create(listOf(dto))) {
            is NetworkResponse.Success -> {
                Log.d(LOGGER_TAG, "upload successful")
                historyDao.insertAll(response.body)
            }
            else -> {
                Log.d(LOGGER_TAG, "upload failed, store locally to be uploaded later")
                storeLocally(dto)
            }
        }
    }

    private suspend fun storeLocally(dto: HistoryToBeUploaded) {
        historyToBeUploadedDao.insert(dto)

        Log.d(LOGGER_TAG, "initialization of worker meant to upload local items")
        val workConstraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadWorker = OneTimeWorkRequestBuilder<HistoryToBeUploadedWorker>()
            .setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(context).beginWith(uploadWorker).enqueue()
    }
}