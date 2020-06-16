package com.easymove.easymove.auth.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class LoginValidator {
    private var isEmailValid = false
    private var isPasswordValid = false

    fun initialize(
        email: MutableLiveData<String>,
        password: MutableLiveData<String>
    ): MediatorLiveData<Boolean> {
        return MediatorLiveData<Boolean>().apply {
            addSource(email) {
                isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                value = isValid()
            }

            addSource(password) {
                isPasswordValid = it.trim().length > 6
                value = isValid()
            }
        }
    }

    private fun isValid() = isEmailValid && isPasswordValid
}