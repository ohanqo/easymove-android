package com.easymove.easymove

import android.app.Application
import com.easymove.easymove.auth.authModule
import com.easymove.easymove.history.historyModule
import com.easymove.easymove.shared.modules.network.ConnectivityObserver
import com.easymove.easymove.shared.modules.network.networkModule
import com.easymove.easymove.shared.utils.utilsModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class App : Application() {

    private val connectivityObserver: ConnectivityObserver by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(networkModule)
            modules(utilsModule)
            modules(authModule)
            modules(historyModule)
        }

        connectivityObserver.listenToConnectivityChange()
    }
}