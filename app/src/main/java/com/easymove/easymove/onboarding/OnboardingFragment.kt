package com.easymove.easymove.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.easymove.easymove.R
import com.easymove.easymove.onboarding.fragments.GDPRFragment
import com.easymove.easymove.onboarding.fragments.QueueFragment
import com.easymove.easymove.onboarding.fragments.SyncFragment
import com.easymove.easymove.shared.extensions.setOnSingleClickListener
import com.easymove.easymove.shared.extensions.visible
import com.easymove.easymove.shared.utils.PrefsUtils
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment(private val prefsUtils: PrefsUtils) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_onboarding, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (prefsUtils.hasAcceptedPolitics) {
            findNavController().navigate(R.id.action_onboardingFragment_to_authFragment)
        }

        val fragmentList = arrayListOf(QueueFragment(), SyncFragment(), GDPRFragment())
        onboarding_viewpager.adapter = OnboardingAdapter(requireActivity(), fragmentList)
        onboarding_viewpager.isUserInputEnabled = false

        onboarding_button.setOnSingleClickListener { onNextClick() }
        onboarding_checkbox.setOnClickListener { showCollectPoliticDialog() }
    }

    private fun showCollectPoliticDialog() {
        OnboardingDialogFragment(::onCollectPoliticConsent).show(
            requireActivity().supportFragmentManager,
            "OnboardingDialogFragment"
        )
    }

    private fun onCollectPoliticConsent() {
        onboarding_checkbox.isChecked = true
        onboarding_button.isClickable = true
        onboarding_button.alpha = 1f
    }

    private fun onNextClick() {
        when (onboarding_viewpager.currentItem) {
            2 -> {
                prefsUtils.hasAcceptedPolitics = true
                findNavController().navigate(R.id.action_onboardingFragment_to_authFragment)
            }
            1 -> {
                onboarding_checkbox.visible()
                onboarding_checkbox.isChecked = false
                onboarding_button.isClickable = false
                onboarding_button.alpha = 0.2f
                onboarding_viewpager.currentItem += 1
            }
            else -> {
                onboarding_viewpager.currentItem += 1
            }
        }
    }
}