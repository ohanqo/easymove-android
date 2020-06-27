package com.easymove.easymove.auth

import com.easymove.easymove.auth.login.LoginRequestDTO
import com.easymove.easymove.auth.register.RegisterRequestDTO

class AuthRepository(private val authService: AuthService) {
    suspend fun login(body: LoginRequestDTO) = authService.login(body)

    suspend fun register(body: RegisterRequestDTO) = authService.register(body)
}