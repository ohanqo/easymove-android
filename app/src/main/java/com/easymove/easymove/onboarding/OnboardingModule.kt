package com.easymove.easymove.onboarding

import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val onboardingModule = module {
    fragment { OnboardingFragment(get()) }
}