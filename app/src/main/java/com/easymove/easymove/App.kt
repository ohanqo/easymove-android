package com.easymove.easymove

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.easymove.easymove.auth.authModule
import com.easymove.easymove.history.HistoryManager
import com.easymove.easymove.history.historyModule
import com.easymove.easymove.onboarding.onboardingModule
import com.easymove.easymove.shared.Constants.LOGGER_TAG
import com.easymove.easymove.shared.modules.database.databaseModule
import com.easymove.easymove.shared.modules.network.ConnectivityObserver
import com.easymove.easymove.shared.modules.network.networkModule
import com.easymove.easymove.shared.utils.PrefsUtils
import com.easymove.easymove.shared.utils.utilsModule
import org.altbeacon.beacon.*
import org.altbeacon.beacon.startup.BootstrapNotifier
import org.altbeacon.beacon.startup.RegionBootstrap
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin


class App : Application(), BeaconConsumer, BootstrapNotifier, RangeNotifier {

    private lateinit var beaconManager: BeaconManager
    private var historyManager: HistoryManager? = null
    private val connectivityObserver: ConnectivityObserver by inject()
    private val prefsUtils: PrefsUtils by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            fragmentFactory()
            modules(utilsModule)
            modules(databaseModule)
            modules(networkModule)
            modules(onboardingModule)
            modules(authModule)
            modules(historyModule)
        }

        connectivityObserver.listenToConnectivityChange()
        beaconManager = BeaconManager.getInstanceForApplication(this)

        if (prefsUtils.isAuthenticated()) {
            startForegroundService()
        }
    }

    override fun didDetermineStateForRegion(p0: Int, p1: Region?) {
        Log.d(LOGGER_TAG, "didDetermineStateForRegion")
    }

    override fun didEnterRegion(p0: Region?) {
        p0?.let {
            Log.d(LOGGER_TAG, "didEnterRegion ${it.uniqueId}")
            historyManager = get()
            beaconManager.startRangingBeaconsInRegion(it)
            beaconManager.addRangeNotifier(this)
        }
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        beacons?.forEach { beacon ->
            if (beacon.distance < 1 && region != null) {
                Log.d(LOGGER_TAG, "didRangeBeaconsInRegion ${region.uniqueId}")
                historyManager?.storeByRegion(region)
            }
        }
    }

    override fun didExitRegion(p0: Region?) {
        p0?.let {
            historyManager = null
            Log.d(LOGGER_TAG, "didExitRegion ${it.uniqueId}")
        }
    }

    override fun onBeaconServiceConnect() {
        Log.d(LOGGER_TAG, "onBeaconServiceConnect")
    }

    fun startForegroundService() {
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, this::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
            .setSmallIcon(R.drawable.easymove_white)
            .setContentTitle("En cours d'exécution")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "My Notification Channel ID",
                "My Notification Name", NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "My Notification Channel Description"
            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            notification.setChannelId(channel.id)
        }

        beaconManager.apply {
            enableForegroundServiceScanning(notification.build(), 121)
            setEnableScheduledScanJobs(false)
            backgroundBetweenScanPeriod = 0
            backgroundScanPeriod = 1100
            bind(this@App)
        }

        val region = Region(
            "Austerlitz",
            null, null, null
        )
        RegionBootstrap(this, region)
    }
}