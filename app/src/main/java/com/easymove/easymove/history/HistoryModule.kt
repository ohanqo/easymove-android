package com.easymove.easymove.history

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val historyModule = module {
    single { get<Retrofit>().create(HistoryService::class.java) }
    viewModel { HistoryViewModel() }
}