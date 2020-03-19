package com.easymove.easymove

import android.app.Application
import com.easymove.easymove.auth.authModule
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(authModule)
        }
    }
}