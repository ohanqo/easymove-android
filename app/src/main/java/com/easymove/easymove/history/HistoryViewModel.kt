package com.easymove.easymove.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

class HistoryViewModel(historyRepository: HistoryRepository) : ViewModel() {
    val historyItems = historyRepository.getHistoryList().cachedIn(viewModelScope)
}