package com.easymove.easymove.shared.utils

import org.koin.dsl.module

val utilsModule = module {
    single { PrefsUtils(get()) }
}