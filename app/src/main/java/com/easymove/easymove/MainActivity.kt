package com.easymove.easymove

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.easymove.easymove.shared.Constants
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!hasGrantedPermission()) {
            askForFineLocationPermission()
            askForBackgroundLocationPermission()
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
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle(getString(R.string.history__permission_denied_title))
                setMessage(getString(R.string.history__permission_denied))
                setPositiveButton(android.R.string.ok, null)
                setOnDismissListener {
                    finish()
                    exitProcess(0)
                }
                show()
            }
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
}