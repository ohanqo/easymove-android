package com.easymove.easymove

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.easymove.easymove.auth.authModule
import com.easymove.easymove.history.HistoryManager
import com.easymove.easymove.history.historyModule
import com.easymove.easymove.onboarding.onboardingModule
import com.easymove.easymove.shared.modules.database.databaseModule
import com.easymove.easymove.shared.modules.network.ConnectivityObserver
import com.easymove.easymove.shared.modules.network.networkModule
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

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, this::class.java), PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(this, BuildConfig.APPLICATION_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Scanning for Beacons")
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

        beaconManager.enableForegroundServiceScanning(notification.build(), 121)
        beaconManager.setEnableScheduledScanJobs(false)
        beaconManager.backgroundBetweenScanPeriod = 0
        beaconManager.backgroundScanPeriod = 1100

        beaconManager.bind(this)

        val region = Region(
            "Austerlitz",
            null, null, null
        )
        RegionBootstrap(this, region)
    }

    override fun didDetermineStateForRegion(p0: Int, p1: Region?) {
        println("§ didDetermineStateForRegion")
    }

    override fun didEnterRegion(p0: Region?) {
        p0?.let {
            println("§ Enter region ${it.uniqueId}")
            historyManager = get()
            beaconManager.startRangingBeaconsInRegion(it)
            beaconManager.addRangeNotifier(this)
        }
    }

    override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, region: Region?) {
        beacons?.forEach { beacon ->
            if (beacon.distance < 1 && region != null) {
                historyManager?.storeByRegion(region)
            }
        }
    }

    override fun didExitRegion(p0: Region?) {
        p0?.let {
            historyManager = null
            println("§ didExitRegion ${it.uniqueId}")
        }
    }

    override fun onBeaconServiceConnect() {
        println("§ onBeaconServiceConnect")
    }
}