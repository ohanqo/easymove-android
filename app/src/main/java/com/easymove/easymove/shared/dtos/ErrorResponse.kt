package com.easymove.easymove.shared.dtos

data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val error: String
)