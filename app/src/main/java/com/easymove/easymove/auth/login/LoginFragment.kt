package com.easymove.easymove.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.easymove.easymove.R
import com.easymove.easymove.databinding.FragmentLoginBinding
import com.easymove.easymove.shared.DataBindingFragment
import com.easymove.easymove.shared.EventObserver
import com.easymove.easymove.shared.dtos.ErrorResponse
import com.easymove.easymove.shared.utils.PrefsUtils
import com.easymove.easymove.shared.utils.ToastUtils

class LoginFragment(
    private val model: LoginViewModel,
    private val prefsUtils: PrefsUtils
) : DataBindingFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentLoginBinding>(R.layout.fragment_login, container).apply {
            viewModel = model
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.onLoginSuccess.observe(viewLifecycleOwner, EventObserver(::onLoginSuccess))
        model.onLoginError.observe(viewLifecycleOwner, EventObserver(::onLoginError))
    }

    private fun onLoginSuccess(loginResponseDTO: LoginResponseDTO) {
        prefsUtils.authToken = loginResponseDTO.token
        findNavController().navigate(R.id.action_loginFragment_to_historyFragment)
    }

    private fun onLoginError(errorResponse: ErrorResponse?) {
        model.password.value = ""

        context?.let {
            val message = errorResponse?.error ?: getString(R.string.auth__login_error)
            ToastUtils.error(it, message)
        }
    }
}