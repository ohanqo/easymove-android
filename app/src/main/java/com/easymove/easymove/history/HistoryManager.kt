package com.easymove.easymove.history

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.altbeacon.beacon.Region


class HistoryManager(private val historyService: HistoryService) {
    var isInTransaction = false
    var region: Region? = null

    fun storeByRegion(region: Region) {
        if (!isInTransaction && this.region?.uniqueId != region.uniqueId) {
            this.region = region
            GlobalScope.launch { store(region) }
        }
    }

    private suspend fun store(region: Region) {
        println("§ true")
        isInTransaction = true

        // Check for Internet
        // historyService.create()
        val dto = CreateHistoryDTO("Navigo TEST", region.uniqueId)
        val response = historyService.create(listOf(dto))
        // TODO: add to database ticket

        println("§ false")
        isInTransaction = false
    }
}