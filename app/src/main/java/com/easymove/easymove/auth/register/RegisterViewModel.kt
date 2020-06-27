package com.easymove.easymove.auth.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easymove.easymove.auth.AuthRepository
import com.easymove.easymove.shared.Event
import com.easymove.easymove.shared.dtos.ErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository,
    registerValidator: RegisterValidator
) : ViewModel() {
    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isFormValid = registerValidator.initialize(username, email, password)

    val onRegisterSuccess = MutableLiveData<Event<RegisterResponseDTO>>()
    val onRegisterError = MutableLiveData<Event<ErrorResponse?>>()

    fun onRegisterClick(view: View) {
        viewModelScope.launch { register() }
    }

    private suspend fun register() {
        val body = RegisterRequestDTO(email.value!!, username.value!!, password.value!!)

        when (val response = authRepository.register(body)) {
            is NetworkResponse.Success -> {
                onRegisterSuccess.value = Event(response.body)
            }
            is NetworkResponse.ServerError -> {
                onRegisterError.value = Event(response.body)
            }
        }
    }
}