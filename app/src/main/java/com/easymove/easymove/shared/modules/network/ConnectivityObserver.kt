package com.easymove.easymove.shared.modules.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.easymove.easymove.BuildConfig
import com.easymove.easymove.shared.extensions.distinct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class ConnectivityObserver(private val context: Context) {
    private val connectivityStatus = MutableLiveData(true)
    private var currentJob: Job? = null

    init {
        listenToConnectivityChange()

        connectivityStatus
            .distinct()
            .observeForever(Observer(::onConnectivityChange))
    }

    fun hasInternet(): Boolean {
        return connectivityStatus.value ?: false
    }

    fun listenToConnectivityChange() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()

        connectivityManager.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network?) = checkForConnection()
                override fun onLost(network: Network?) = checkForConnection()
            }
        )
    }

    private fun checkForConnection() {
        currentJob?.let { if (it.isActive) return }

        currentJob = GlobalScope.launch(Dispatchers.IO) {
            val url = URL(BuildConfig.API_URL)

            try {
                val urlConnection = url.openConnection() as HttpURLConnection

                urlConnection.apply {
                    connectTimeout = 2000
                    connect()

                    if (responseCode == 404) {
                        disconnect()
                        connectivityStatus.postValue(true)
                    } else {
                        connectivityStatus.postValue(false)
                    }
                }
            } catch (e: IOException) {
                connectivityStatus.postValue(false)
            }
        }
    }

    private fun onConnectivityChange(hasInternet: Boolean) {
        if (!hasInternet) {
            Toast.makeText(context, "Vous n'êtes pas connecté !", Toast.LENGTH_LONG).show()
        }
    }
}