package com.example.blogdeinternet.system.configuration

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper

class InternetConnectivityChecker(private val context: Context) {

    private val handler = Handler(Looper.getMainLooper())
    private var isConnected = false
    private var callback: ((Boolean) -> Unit)? = null

    private val connectivityCheckRunnable = object : Runnable {
        override fun run() {
            isConnected = isInternetAvailable(context)
            callback?.invoke(isConnected)
            handler.postDelayed(this, 5000) // Ejecutar cada 5 segundos
        }
    }

    fun startCheckingConnectivity(callback: (Boolean) -> Unit) {
        this.callback = callback
        handler.post(connectivityCheckRunnable)
    }

    fun stopCheckingConnectivity() {
        handler.removeCallbacks(connectivityCheckRunnable)
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                     capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}
