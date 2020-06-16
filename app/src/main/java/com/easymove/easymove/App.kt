package com.easymove.easymove

import android.app.Application
import com.easymove.easymove.auth.authModule
import com.easymove.easymove.shared.modules.network.ConnectivityObserver
import com.easymove.easymove.shared.modules.network.networkModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    private val connectivityObserver: ConnectivityObserver by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule)
            modules(authModule)
        }

        connectivityObserver.listenToConnectivityChange()
    }
}