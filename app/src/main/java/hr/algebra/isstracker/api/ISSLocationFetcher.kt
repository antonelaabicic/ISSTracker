package hr.algebra.isstracker.api

import android.content.Context
import hr.algebra.isstracker.DataReceiver
import hr.algebra.isstracker.framework.sendBroadcast

class ISSLocationFetcher(private val context: Context) {
    fun fetchISSLocation() {
        // fake work
        Thread.sleep(1000)
        context.sendBroadcast<DataReceiver>()
    }
}