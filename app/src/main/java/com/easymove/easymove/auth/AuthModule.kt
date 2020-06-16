package com.easymove.easymove.auth

import com.easymove.easymove.auth.login.LoginValidator
import com.easymove.easymove.auth.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { get<Retrofit>().create(AuthService::class.java) }
    single { AuthRepository(get()) }
    single { LoginValidator() }
    viewModel { LoginViewModel(get(), get()) }
}