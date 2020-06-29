package com.easymove.easymove.history

import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val historyModule = module {
    single { get<Retrofit>().create(HistoryService::class.java) }
    factory { HistoryManager(get(), get(), get(), get(), get()) }
    single { HistoryPagingMediator(get(), get(), get(), get()) }
    single { HistoryRepository(get(), get()) }
    viewModel { HistoryViewModel(get()) }
    fragment { HistoryFragment() }
}