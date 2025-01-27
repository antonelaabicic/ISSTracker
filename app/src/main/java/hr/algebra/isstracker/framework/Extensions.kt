package hr.algebra.isstracker.framework

import android.annotation.SuppressLint
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
import hr.algebra.isstracker.ASTRONAUT_CONTENT_URI
import hr.algebra.isstracker.DataReceiver
import hr.algebra.isstracker.model.Astronaut
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

inline fun <reified T : Activity>Context.startActivity() {
    startActivity(
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
    )
}

inline fun<reified T : Activity> Context.startActivity(key: String, value: Int) {
    startActivity(
        Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(key, value)
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

fun Context.fetchAstronauts(callback: (MutableList<Astronaut>) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val items = mutableListOf<Astronaut>()

        val cursor = contentResolver.query(
            ASTRONAUT_CONTENT_URI, null, null, null, null
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(Astronaut::_id.name)
            val nameIndex = it.getColumnIndex(Astronaut::name.name)
            val countryIndex = it.getColumnIndex(Astronaut::country.name)
            val flagCodeIndex = it.getColumnIndex(Astronaut::flagCode.name)
            val agencyIndex = it.getColumnIndex(Astronaut::agency.name)
            val positionIndex = it.getColumnIndex(Astronaut::position.name)
            val daysInSpaceIndex = it.getColumnIndex(Astronaut::daysInSpace.name)
            val descriptionIndex = it.getColumnIndex(Astronaut::description.name)
            val urlIndex = it.getColumnIndex(Astronaut::url.name)
            val imageIndex = it.getColumnIndex(Astronaut::image.name)
            val instagramIndex = it.getColumnIndex(Astronaut::instagram.name)
            val twitterIndex = it.getColumnIndex(Astronaut::twitter.name)
            val facebookIndex = it.getColumnIndex(Astronaut::facebook.name)
            val isFavoriteIndex = it.getColumnIndex(Astronaut::isFavorite.name)

            while (it.moveToNext()) {
                items.add(
                    Astronaut(
                        it.getLong(idIndex),
                        it.getString(nameIndex),
                        it.getString(countryIndex),
                        it.getString(flagCodeIndex),
                        it.getString(agencyIndex),
                        it.getString(positionIndex),
                        it.getDouble(daysInSpaceIndex),
                        it.getString(descriptionIndex),
                        it.getString(urlIndex),
                        it.getString(imageIndex),
                        it.getString(instagramIndex),
                        it.getString(twitterIndex),
                        it.getString(facebookIndex),
                        it.getString(isFavoriteIndex).toBoolean()
                    )
                )
            }
        }
        callback(items)
    }
}