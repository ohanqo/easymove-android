package com.easymove.easymove.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnboardingAdapter(fragmentActivity: FragmentActivity, private val fragments: List<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment = this.fragments[position]

    override fun getItemCount(): Int = fragments.size
}
