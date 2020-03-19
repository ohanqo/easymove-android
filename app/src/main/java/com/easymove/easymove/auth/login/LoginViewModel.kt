package com.easymove.easymove.auth.login

import androidx.lifecycle.ViewModel
import com.easymove.easymove.auth.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {
    fun sayHello() {
        println("§ Hello !")
    }
}