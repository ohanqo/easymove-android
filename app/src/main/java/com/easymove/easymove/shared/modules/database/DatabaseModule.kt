package com.easymove.easymove.shared.modules.database

import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.create(get()) }
    single { get<AppDatabase>().history() }
    single { get<AppDatabase>().historyRemoteKeys() }
    single { get<AppDatabase>().historyToBeUploaded() }
}