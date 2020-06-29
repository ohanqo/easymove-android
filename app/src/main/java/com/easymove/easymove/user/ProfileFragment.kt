package com.easymove.easymove.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.easymove.easymove.App
import com.easymove.easymove.R
import com.easymove.easymove.shared.extensions.setOnSingleClickListener
import com.easymove.easymove.shared.modules.database.AppDatabase
import com.easymove.easymove.shared.utils.PrefsUtils
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment(
    private val database: AppDatabase,
    private val prefsUtils: PrefsUtils
) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profile_logout_button.setOnSingleClickListener {
            context?.let {
                AlertDialog.Builder(it).apply {
                    setTitle(getString(R.string.logout))
                    setMessage(getString(R.string.logout_message))
                    setPositiveButton(R.string.logout) { _, _ ->
                        logout()
                    }
                    setOnDismissListener { dialog ->
                        dialog.dismiss()
                    }
                    show()
                }
            }
        }
    }

    private fun logout() {
        GlobalScope.launch {
            database.clearAllTables()
            prefsUtils.clearPreferences()
            (context?.applicationContext as App?)?.stopForegroundService()
            findNavController().navigate(R.id.action_profileFragment_to_onboardingFragment)
        }
    }
}