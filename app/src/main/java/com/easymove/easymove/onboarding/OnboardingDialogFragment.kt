package com.easymove.easymove.onboarding

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.easymove.easymove.R
import com.easymove.easymove.shared.extensions.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_onboarding_dialog.*

class OnboardingDialogFragment(val onConsent: () -> Unit) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_onboarding_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(R.drawable.onboarding_dialog_background)

        dialog?.setCanceledOnTouchOutside(false)

        onboarding_dialog_button.setOnSingleClickListener {
            dialog?.let {
                it.dismiss()
                onConsent()
            }
        }
    }

    override fun onResume() {
        val window = dialog!!.window!!
        val size = Point()
        // Store dimensions of the screen in `size`
        val display = window.windowManager.defaultDisplay
        display.getSize(size)
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((size.x * 0.85).toInt(), (size.y * 0.85).toInt())
        window.setGravity(Gravity.CENTER)
        // Call super onResume after sizing
        super.onResume()
    }
}