package com.easymove.easymove.user

data class User(
    val id: String,
    val email: String,
    val username: String,
    val role: UserRole,
    val createdAt: String,
    val updatedAt: String
)