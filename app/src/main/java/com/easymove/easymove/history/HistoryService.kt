package com.easymove.easymove.history

import com.easymove.easymove.shared.dtos.ErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HistoryService {
    @GET("history/{filter}/{page}")
    suspend fun getHistory(
        @Path("filter") filter: String,
        @Path("page") page: Int
    ): NetworkResponse<HistoryFilterResponseDTO, ErrorResponse>

    @POST("history")
    suspend fun create(
        @Body historyList: List<CreateHistoryDTO>
    ): NetworkResponse<List<History>, ErrorResponse>
}