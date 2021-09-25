package com.example.artbook.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData


object NetworkLiveData : LiveData<Boolean>() {

    private lateinit var application: Application
    private lateinit var networkRequest: NetworkRequest

    override fun onActive() {
        super.onActive()
        getDetails()
    }

    fun getApplication(): Application {
        return application
    }

    fun init(application: Application) {
        NetworkLiveData.application = application
        networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()
    }

    private fun getDetails() {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                postValue(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                postValue(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                postValue(false)
            }
        })
    }

    fun isNetworkAvailable(): Boolean {
        var connected: Boolean = false
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetwork
        network?.let {
            val networkCapabilities = cm.getNetworkCapabilities(it)
            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    connected = true
                }
            }
        }
        return connected
    }
}