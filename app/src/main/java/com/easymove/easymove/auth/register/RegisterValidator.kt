package com.easymove.easymove.auth.register

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

class RegisterValidator {
    private var isUsernameValid = false
    private var isEmailValid = false
    private var isPasswordValid = false

    fun initialize(
        username: MutableLiveData<String>,
        email: MutableLiveData<String>,
        password: MutableLiveData<String>
    ): MediatorLiveData<Boolean> {
        return MediatorLiveData<Boolean>().apply {
            addSource(username) {
                isUsernameValid = it.trim().length > 2
                value = isValid()
            }

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

    private fun isValid() = isUsernameValid && isEmailValid && isPasswordValid
}