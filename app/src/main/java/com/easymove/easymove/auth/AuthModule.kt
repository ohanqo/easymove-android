package com.easymove.easymove.auth

import com.easymove.easymove.auth.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthService() }
    single { AuthRepository(get()) }
    viewModel { LoginViewModel(get()) }
}