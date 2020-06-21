package com.easymove.easymove.history

import retrofit2.http.GET
import retrofit2.http.Path

interface HistoryService {
    @GET("history/{filter}/{page}")
    suspend fun getHistory(
        @Path("filter") filter: String,
        @Path("page") page: Int
    ): HistoryFilterResponseDTO
}