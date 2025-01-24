package hr.algebra.isstracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.isstracker.framework.setBooleanPreference
import hr.algebra.isstracker.framework.startActivity

class DataReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //context.setBooleanPreference(DATA_IMPORTED_ISS_LOCATION)
        //context.setBooleanPreference(DATA_IMPORTED_ASTRONAUTS)
        context.startActivity<MainActivity>()
    }
}