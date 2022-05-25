package com.enesakkal.foodToGo.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

/*

************ STATE FLOW******************

--> State flow is a part of Kotlin flow Api that enables flow to optimally emit state updates and emit values to multiple consumers.

1-)State flow - read only --> biggest advantage is that it's always active and in memory it becomes eligible for garbage collection
only when there are no other references to it from a garbage collection root +++ it requires an initial state to be passed in the constructor while liveData does not
when a new consumer start collecting from the flow, it receives the last state in the stream.

--> State flow represent a hot stream of data --> the main difference is that stateflow stays active in the background.That's why we call it hot.
2-)MutableState flow


 */
@ExperimentalCoroutinesApi
class NetworkListener: ConnectivityManager.NetworkCallback() {

    private val isNetWorkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)

        var isConnected = false
        connectivityManager.allNetworks.forEach { network ->
            val networkCapability = connectivityManager.getNetworkCapabilities(network)
            networkCapability.let {
                if (it != null) {
                    if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                        isConnected = true
                        return@forEach
                    }
                }
            }
        }

            isNetWorkAvailable.value = isConnected

            return isNetWorkAvailable

        }


    override fun onAvailable(network: Network) {
        isNetWorkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetWorkAvailable.value = false
    }
}