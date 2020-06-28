package com.easymove.easymove.onboarding.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.easymove.easymove.R
import kotlinx.android.synthetic.main.item_onboarding.*

class SyncFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.item_onboarding, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        item_onboarding_image.setImageResource(R.drawable.ic_onboarding_sync)
        item_onboarding_text.text = getString(R.string.onboarding_sync)
    }
}