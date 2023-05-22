package com.example.ktsreddit.data.network

import android.content.Context
import android.net.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object NetworkStatusTracker {
    private var connectivityManager: ConnectivityManager? = null

    lateinit var networkStatus: Flow<Boolean>

    fun init(context: Context) {
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkStatus = callbackFlow<Boolean> {

            if (getCurrentStatus()) {
                trySend(true)
            } else {
                trySend(false)
            }

            val networkStatusCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onUnavailable() {
                    trySend(false).isSuccess
                }

                override fun onAvailable(network: Network) {
                    trySend(true).isSuccess
                }

                override fun onLost(network: Network) {
                    trySend(false).isSuccess
                }
            }

            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager!!.registerNetworkCallback(request, networkStatusCallback)

            awaitClose {
                connectivityManager!!.unregisterNetworkCallback(networkStatusCallback)
            }

        }

    }


    fun getCurrentStatus(): Boolean {
        val network = connectivityManager!!.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager!!.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    }


}
/*
inline fun <Result> Flow<NetworkStatus>.map(
    crossinline onUnavailable: suspend () -> Result,
    crossinline onAvailable: suspend () -> Result,
): Flow<Result> = map { status ->
    when (status) {
        NetworkStatus.Unavailable -> onUnavailable()
        NetworkStatus.Available -> onAvailable()
    }
}

 */