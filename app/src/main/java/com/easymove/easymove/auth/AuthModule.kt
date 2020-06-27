package com.easymove.easymove.auth

import com.easymove.easymove.auth.login.LoginFragment
import com.easymove.easymove.auth.login.LoginValidator
import com.easymove.easymove.auth.login.LoginViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { get<Retrofit>().create(AuthService::class.java) }
    single { AuthRepository(get()) }
    single { LoginValidator() }
    viewModel { LoginViewModel(get(), get()) }
    fragment { AuthFragment(get()) }
    fragment { LoginFragment(get(), get()) }
}