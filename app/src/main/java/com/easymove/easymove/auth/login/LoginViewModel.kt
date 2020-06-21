package com.easymove.easymove.auth.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easymove.easymove.auth.AuthRepository
import com.easymove.easymove.shared.Event
import com.easymove.easymove.shared.dtos.ErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    loginValidator: LoginValidator
) : ViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isFormValid = loginValidator.initialize(email, password)

    val onLoginSuccess = MutableLiveData<Event<LoginResponseDTO>>()
    val onLoginError = MutableLiveData<Event<ErrorResponse?>>()

    fun onLoginClick(view: View) {
        viewModelScope.launch { login() }
    }

    private suspend fun login() {
        val body = LoginRequestDTO(email.value!!, password.value!!)

        when (val response = authRepository.login(body)) {
            is NetworkResponse.Success -> {
                onLoginSuccess.value = Event(response.body)
            }
            is NetworkResponse.ServerError -> {
                onLoginError.value = Event(response.body)
            }
        }
    }
}