package com.toletspot.houseforrent

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class NetworkStatus {
    WifiWithInternet,
    WifiNoInternet,
    CellularWithInternet,
    CellularNoInternet,
    Online,
    Offline
}

class NetworkMonitor2(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _status = MutableStateFlow(getInitialStatus(connectivityManager))
    val status = _status.asStateFlow()

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            updateNetworkStatus(network)
        }

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            updateNetworkStatus(network)
        }

        override fun onLost(network: Network) {
            _status.value = NetworkStatus.Offline
        }
    }

    private fun updateNetworkStatus(network: Network) {
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return

        val hasInternet = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        val isWifi = caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val isCellular = caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

        _status.value = when {
            (isWifi && hasInternet) || (isCellular && hasInternet) -> NetworkStatus.Online
            else -> NetworkStatus.Offline
        }
    }

    private fun getInitialStatus(cm: ConnectivityManager): NetworkStatus {
        val network = cm.activeNetwork ?: return NetworkStatus.Offline
        val caps = cm.getNetworkCapabilities(network) ?: return NetworkStatus.Offline

        val hasInternet = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        val isWifi = caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val isCellular = caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

        return when {
            isWifi && hasInternet -> NetworkStatus.WifiWithInternet
            isWifi && !hasInternet -> NetworkStatus.WifiNoInternet
            isCellular && hasInternet -> NetworkStatus.CellularWithInternet
            isCellular && !hasInternet -> NetworkStatus.CellularNoInternet
            caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> NetworkStatus.Online
            else -> NetworkStatus.Offline
        }
    }

    fun register() {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

class NetworkMonitor1(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _status = MutableStateFlow(NetworkStatus.Offline)
    val status = _status.asStateFlow()

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            updateNetworkStatus(network)
        }

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            updateNetworkStatus(network)
        }

        override fun onLost(network: Network) {
            _status.value = NetworkStatus.Offline
        }
    }

    private fun updateNetworkStatus(network: Network) {
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return

        val hasInternet = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        val isWifi = caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val isCellular = caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

        _status.value = when {
            (isWifi && hasInternet) || (isCellular && hasInternet) -> NetworkStatus.Online
            else -> NetworkStatus.Offline
        }

    }

    fun register() {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

@Composable
fun rememberNetworkStatus2(): State<NetworkStatus> {
    val context = LocalContext.current
    val monitor = remember { NetworkMonitor(context) }
    val state by monitor.status.collectAsState()

    DisposableEffect(monitor) {
        monitor.register()
        onDispose { monitor.unregister() }
    }

    return remember {  derivedStateOf { state } }
}

@Composable
fun rememberNetworkStatus1(): State<NetworkStatus> {
    val context = LocalContext.current
    val monitor = remember { NetworkMonitor(context) }
    val state = monitor.status.collectAsState()

    DisposableEffect(Unit) {
        monitor.register()
        onDispose { monitor.unregister() }
    }
    return state
}

class NetworkMonitor(context: Context) {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _status = MutableStateFlow(getInitialStatus(connectivityManager))
    val status = _status.asStateFlow()

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            updateNetworkStatus(network)
        }

        override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
            updateNetworkStatus(network)
        }

        override fun onLost(network: Network) {
            _status.value = NetworkStatus.Offline
        }
    }

    private fun updateNetworkStatus(network: Network) {
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return

        val hasInternet = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        val isWifi = caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val isCellular = caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

        _status.value = when {
            (isWifi && hasInternet) || (isCellular && hasInternet) -> NetworkStatus.Online
            else -> NetworkStatus.Offline
        }
    }

    private fun getInitialStatus(cm: ConnectivityManager): NetworkStatus {
        val network = cm.activeNetwork ?: return NetworkStatus.Offline
        val caps = cm.getNetworkCapabilities(network) ?: return NetworkStatus.Offline

        val hasInternet = caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        val isWifi = caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val isCellular = caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

        return if ((isWifi && hasInternet) || (isCellular && hasInternet)) {
            NetworkStatus.Online
        } else {
            NetworkStatus.Offline
        }
    }

    fun register() {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    fun unregister() {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

@Composable
fun rememberNetworkStatus(): State<NetworkStatus> {
    val context = LocalContext.current
    val monitor = remember { NetworkMonitor(context) }
    val networkStatus by monitor.status.collectAsState()

    DisposableEffect(monitor) {
        monitor.register()
        onDispose { monitor.unregister() }
    }

    return derivedStateOf { networkStatus }
}
