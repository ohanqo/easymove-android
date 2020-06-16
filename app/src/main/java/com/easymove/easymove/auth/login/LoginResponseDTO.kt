package com.easymove.easymove.auth.login

import com.easymove.easymove.user.User

data class LoginResponseDTO(val user: User, val token: String)