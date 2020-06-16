package com.easymove.easymove.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.easymove.easymove.R
import com.easymove.easymove.databinding.FragmentLoginBinding
import com.easymove.easymove.shared.DataBindingFragment
import org.koin.android.viewmodel.ext.android.getViewModel

class LoginFragment : DataBindingFragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = binding(R.layout.fragment_login, container)
        viewModel = getViewModel()

        binding.viewModel = viewModel
        return binding.root
    }
}