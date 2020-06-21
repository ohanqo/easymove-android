package com.easymove.easymove.history

data class HistoryFilterResponseDTO(
    val totalPages: Int,
    val totalHistoryItems: Int,
    val page: Int,
    val pageHistoryItems: List<History>
)