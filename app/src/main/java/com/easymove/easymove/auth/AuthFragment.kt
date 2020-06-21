package com.easymove.easymove.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.easymove.easymove.BuildConfig
import com.easymove.easymove.R
import com.easymove.easymove.shared.extensions.awaitTransitionComplete
import com.easymove.easymove.shared.extensions.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_auth.*
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth_version_code.text = BuildConfig.VERSION_NAME

        auth_motion_layout.transitionToState(R.id.auth_fade_in_end)

        auth_login_button.setOnSingleClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                auth_motion_layout.transitionToState(R.id.auth_fade_in_start)
                auth_motion_layout.awaitTransitionComplete(R.id.auth_fade_in_start)
                findNavController().navigate(R.id.action_authFragment_to_loginFragment)
            }
        }
    }
}