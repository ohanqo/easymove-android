package com.easymove.easymove.history

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.easymove.easymove.shared.Constants.HISTORY_FILTER_ITEMS_PER_PAGE

class HistoryRepository(
    private val historyDao: HistoryDao,
    private val historyPagingMediator: HistoryPagingMediator
) {
    fun getHistoryList() = Pager(
        config = PagingConfig(HISTORY_FILTER_ITEMS_PER_PAGE),
        remoteMediator = historyPagingMediator
    ) {
        historyDao.getAll()
    }.flow
}