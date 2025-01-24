package hr.algebra.isstracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.isstracker.framework.setBooleanPreference
import hr.algebra.isstracker.framework.startActivity

class DataReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED) // we'll see
        context.startActivity<MainActivity>()
    }
}