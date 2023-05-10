package com.example.ktsreddit.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.ktsreddit.data.network.model.NetworkStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

object NetworkStatusTracker{
    private var connectivityManager : ConnectivityManager? = null

    val networkStatus = callbackFlow<Boolean> {
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

    fun init(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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