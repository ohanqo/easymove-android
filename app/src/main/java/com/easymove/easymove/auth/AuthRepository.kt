package com.easymove.easymove.auth

import com.easymove.easymove.auth.login.LoginRequestDTO

class AuthRepository(private val authService: AuthService) {
    suspend fun login(body: LoginRequestDTO) = authService.login(body)
}