package com.easymove.easymove.user

import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val userModule = module {
    fragment { ProfileFragment(get(), get()) }
}