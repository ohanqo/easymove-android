package com.easymove.easymove.auth

import com.easymove.easymove.auth.login.LoginRequestDTO
import com.easymove.easymove.auth.login.LoginResponseDTO
import com.easymove.easymove.shared.dtos.ErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestDTO): NetworkResponse<LoginResponseDTO, ErrorResponse>
}