package com.easymove.easymove.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.easymove.easymove.R
import com.easymove.easymove.databinding.FragmentRegisterBinding
import com.easymove.easymove.shared.DataBindingFragment
import com.easymove.easymove.shared.EventObserver
import com.easymove.easymove.shared.dtos.ErrorResponse
import com.easymove.easymove.shared.utils.ToastUtils


class RegisterFragment(private val model: RegisterViewModel) : DataBindingFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentRegisterBinding>(R.layout.fragment_register, container).apply {
            viewModel = model
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.onRegisterSuccess.observe(viewLifecycleOwner, EventObserver(::onRegisterSuccess))
        model.onRegisterError.observe(viewLifecycleOwner, EventObserver(::onRegisterError))
    }

    private fun onRegisterSuccess(registerResponseDTO: RegisterResponseDTO) {
        context?.let { Toast.makeText(it, registerResponseDTO.message, Toast.LENGTH_SHORT).show() }
        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
    }

    private fun onRegisterError(errorResponse: ErrorResponse?) {
        context?.let {
            val message = errorResponse?.message ?: getString(R.string.auth__register_error)
            ToastUtils.error(it, message)
        }
    }
}