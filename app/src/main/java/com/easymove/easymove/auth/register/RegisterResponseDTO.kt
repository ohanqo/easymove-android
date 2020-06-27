package com.easymove.easymove.auth.register

import com.easymove.easymove.user.User

data class RegisterResponseDTO(val message: String, val savedUser: User)