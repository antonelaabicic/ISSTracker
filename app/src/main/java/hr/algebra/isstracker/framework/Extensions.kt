package hr.algebra.isstracker.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.isstracker.DataReceiver

inline fun <reified T : Activity>Context.startActivity() {
    startActivity(
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    )
}

fun Context.getBooleanPreference(key: String) =
    PreferenceManager
        .getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun Context.setBooleanPreference(key: String, value: Boolean = true) =
    PreferenceManager
        .getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key, value)
        .apply()

fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { cap ->
            return cap.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || cap.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

fun callDelayed(delay: Long, work: Runnable) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() {
    this.sendBroadcast(
        Intent(this, DataReceiver::class.java)
    )
}