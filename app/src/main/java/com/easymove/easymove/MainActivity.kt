package com.easymove.easymove

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.easymove.easymove.shared.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!doesDeviceSupportBluetooth()) {
            showFailureDialog(
                R.string.bluetooth__not_supported_alert_title,
                R.string.bluetooth__not_supported_alert_subtitle
            )
        }

        if (!hasGrantedPermission()) {
            askForFineLocationPermission()
            askForBackgroundLocationPermission()
        }
    }

    override fun onStart() {
        super.onStart()

        val navigation = Navigation.findNavController(
            this,
            R.id.main_nav_host_fragment
        )

        main_bottom_navigation_view.setupWithNavController(navigation)

        navigation.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.label == "fragment_history") {
                activity_main_motion.transitionToState(R.id.bottom_navigation_visible)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val hasGrantedPermission =
            grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        if (!hasGrantedPermission) {
            showFailureDialog(
                R.string.bluetooth__permission_denied_alert_title,
                R.string.bluetooth__permission_denied_alert_subtitle
            )
        }
    }

    private fun askForFineLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            Constants.ACCESS_FINE_LOCATION_PERMISSION_RESULT_CODE
        )
    }

    private fun askForBackgroundLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                Constants.ACCESS_BACKGROUND_LOCATION_PERMISSION_RESULT_CODE
            )
        }
    }

    private fun hasGrantedPermission(): Boolean {
        val locationPermission = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val backgroundLocationPermission = ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )

            return locationPermission == PackageManager.PERMISSION_GRANTED && backgroundLocationPermission == PackageManager.PERMISSION_GRANTED
        }

        return locationPermission == PackageManager.PERMISSION_GRANTED
    }

    private fun doesDeviceSupportBluetooth(): Boolean {
        return BluetoothAdapter.getDefaultAdapter() != null
    }

    private fun showFailureDialog(titleId: Int, subtitleId: Int) {
        AlertDialog.Builder(this).apply {
            setTitle(getString(titleId))
            setMessage(getString(subtitleId))
            setPositiveButton(android.R.string.ok, null)
            setOnDismissListener {
                finish()
                exitProcess(0)
            }
            show()
        }
    }
}